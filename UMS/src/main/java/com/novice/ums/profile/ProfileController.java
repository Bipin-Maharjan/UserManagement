/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.profile;

import com.novice.ums.helper.Helper;
import com.novice.ums.model.HistoryDAO;
import com.novice.ums.model.User;
import com.novice.ums.model.UserDAO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author HP Controller class is only used for handling the user interaction /
 * user input and redirect to respective class VIEW: Is a jsp page and is only a
 * UI page
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profile/*"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProfileController extends HttpServlet {

    private List errors = new ArrayList();
    private String success = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URI[] = request.getRequestURI().split("/");
        switch (URI.length) {
            case 3:
                // view profile or do some operation in the others profile
                String parameter = request.getParameter("user");
                if (parameter != null) {
                    // view others profile or do some operation in tother profile
                    String[] splitParameter = parameter.split("/");
                    switch (splitParameter.length) {
                        case 1:
                            // view others profile
                            User otherUser = new UserDAO().getUser(splitParameter[0]);
                            // if there is any error in UserDAO  otherUser will be null
                            if (otherUser == null) {
                                response.sendError(500, "Databse Error while fetching user");
                                return;
                            }
                            request.setAttribute("otherUser", otherUser);
                            viewPage(request, response, "/othersprofile.jsp");
                            break;
                        case 2:
                            // do some operation in others profile
                            doOperation(request, response, splitParameter[0], splitParameter[1]);
                            break;
                        default:
                            //Invalid url
                            response.sendError(404, "Invalid URL. Please enter a valid URL.");
                            return;
                    }
                } else {
                    // view my profile
                    viewPage(request, response, "/myprofile.jsp");
                }
                break;
            case 4:
                if (URI[3].equalsIgnoreCase("editprofile")) {
                    viewPage(request, response, "/editprofile.jsp");
                    return;
                }
            // else
            //continue to default
            default:
                response.sendError(404, "Invalid URL. Please enter a valid URL.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] URI = request.getRequestURI().split("/");

        switch (URI.length) {
            case 4:
                switch (URI[3]) {
                    case "uploadprofilepicture":
                        uploadProfilePicture(request, response);
                        break;
                    case "updatedetails":
                        updateDetails(request, response);
                        break;
                    case "changepassword":
                        changePassword(request, response);
                        break;
                    case "changequestion":
                        changeQuestions(request, response);
                        break;
                    default:
                        this.errors.add("Invalid Request");
                        response.sendRedirect(request.getContextPath() + "/profile");
                }
                break;
            default:
                this.errors.add("Invalid Request");
                response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    private void uploadProfilePicture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        * This will handel the profile picture upload with verification of image
         */

        String folderPath = getServletContext().getRealPath("") + "resources\\profile\\";
        Part filepart;

        try {
            filepart = request.getPart("profile_picture");
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            this.errors.add("File size greater than 5MB.");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        String filename = filepart.getSubmittedFileName().replaceAll("\\s", "");
        String splitFilename[] = filename.split("\\.");
        String ext = splitFilename[splitFilename.length - 1].trim();

        // checking File type and handeling error
        if (!ext.equalsIgnoreCase("jpg") && !ext.equalsIgnoreCase("png")
                && !ext.equalsIgnoreCase("jpeg") && !ext.equalsIgnoreCase("jifi")) {
            this.errors.add("Image type is not JPG/PNG.");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        //Renaming if same name file exists
        File newImage = new File(folderPath + filename);
        while (newImage.exists()) {
            filename = filename.split("\\.([^.]*)$")[0].trim() + "1." + ext;
            newImage = new File(folderPath + filename);
        }

        User updateUser = new User();
        updateUser.setProfile_picture(filename);
        updateUser = new UserDAO().updateUser(updateUser, user.getUsername());

        // is there is any error in UserDAO  updateUser will be null
        if (updateUser == null) {
            response.sendError(500, "Databse Error while updating profile picture");
            return;
        }

        //save file in location
        filepart.write(folderPath + filename);
        //update session data
        session.setAttribute("user", updateUser);

        //keeping log on admins account
        new HistoryDAO().keepLog(updateUser.getUsername(), "profile update", "Profile picture updated : " + updateUser.getUsername(), request.getRemoteAddr());

        //deleting previous profile picture (if any)
        if (user.getProfile_picture() != null) {
            File oldImage = new File(folderPath + user.getProfile_picture());
            if (oldImage.exists()) {
                oldImage.delete();
            }
        }
        if(this.errors.isEmpty()){
            this.success = "Profile picture has been updated successfully";
        }
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    private void updateDetails(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List list = new ArrayList();
        
        String first_name,last_name,email,username,phone_number,date_of_birth,bio,log;
        HttpSession session = request.getSession();
        
        first_name = request.getParameter("fname").trim();
        last_name = request.getParameter("lname").trim();
        email = request.getParameter("email").trim();
        username = request.getParameter("username").toLowerCase().trim();
        phone_number = request.getParameter("contact").trim();
        date_of_birth = request.getParameter("dob").trim();
        bio = request.getParameter("bio").trim();
        
        log = "";
        
        User user = (User) session.getAttribute("user");
        
        User updateUser = new User();
        if(!first_name.equalsIgnoreCase("")){
            if(!first_name.equalsIgnoreCase(user.getFirst_name())){
                updateUser.setFirst_name(first_name);
                log += "Firstname";
            }
        }
        else{
            list.add("First Name is invalid");
        }
        
        if(!last_name.equalsIgnoreCase("")){
            if(!last_name.equalsIgnoreCase(user.getLast_name())){
                updateUser.setLast_name(last_name);
                log += log.equalsIgnoreCase("")?"Lastname ":",Lastname" ;
            }
        }
        else{
            list.add("Last name is invalid");
        }
        
        if(!email.equalsIgnoreCase("")){
            if(!email.equalsIgnoreCase(user.getEmail())){
                updateUser.setEmail(email);
                log += log.equalsIgnoreCase("")?"Email ":",Email";
            }
        }
        else{
            list.add("Email is invalid");
        }
        
        if(!bio.equalsIgnoreCase(user.getBio())){
            updateUser.setBio(bio);
            log += log.equalsIgnoreCase("")?"Bio ":",Bio";
        }
        
        if(!date_of_birth.equalsIgnoreCase("")){
            if(!date_of_birth.equalsIgnoreCase(user.getDate_of_birth())){
                updateUser.setDate_of_birth(date_of_birth);
                log += log.equalsIgnoreCase("")?"Date of birth ":",Date of birth";
            }
        }
        else{
            list.add("Date of birth is invalid");
        }
        
        //update userdetails
        UserDAO dao = new UserDAO();
        updateUser = dao.updateUser(updateUser, user.getUsername());
        if(updateUser == null){
            response.sendError(500, "Error While updating User Details");
            return;
        }
        //keep log
        if(!log.equalsIgnoreCase("")){
            //keeping log on users account 
            new HistoryDAO().keepLog(updateUser.getUsername(), "user details",log+" updated", request.getRemoteAddr());
        }
        
        //update Phone number
        if(phone_number.matches("^(\\+977|[0-9])[0-9]{9,10}$") && (phone_number.length() == 10 || phone_number.length() == 14)){
            if(!phone_number.equalsIgnoreCase(user.getPhone_number())){
                updateUser = new User();
                updateUser.setPhone_number(phone_number);
                updateUser = dao.updateUser(updateUser, user.getUsername());
                if(updateUser == null){
                    response.sendError(500, "Error While updating User Details");
                    return;
                }
                //keeping log on users account 
                new HistoryDAO().keepLog(updateUser.getUsername(), "user details","Phone number updated", request.getRemoteAddr());
            }
        }
        else{
            list.add("Invalid phone number");
        }
        
        //update Username
        if(!user.getUsername().equalsIgnoreCase(username) && !username.equals("")){
            if(dao.isUsernameAvailable(username)){
                updateUser = new User();
                updateUser.setUsername(username);
                updateUser = dao.updateUser(updateUser, user.getUsername());
                if(updateUser == null){
                    response.sendError(500, "Error While updating User Details");
                    return;
                }
                //keeping log on users account 
                new HistoryDAO().keepLog(updateUser.getUsername(), "user details","Username updated", request.getRemoteAddr());
            }
            else{
                list.add("Username already Taken.");
            }
        }
        
        session.setAttribute("user", updateUser);
        manageError(1,list);
        request.setAttribute("errors", this.errors);
        if(list.isEmpty()){
            this.success = " Details are updated successfully";
        }
        
        response.sendRedirect(request.getContextPath() + "/profile/editprofile");
    }
            
    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List list  = new ArrayList();
        
        String currentPassword,newPassword,confirmPassword;
        HttpSession session = request.getSession();
        
        currentPassword = Helper.hashPassword(request.getParameter("currentpass"));
        newPassword = Helper.hashPassword(request.getParameter("newpass"));
        confirmPassword = Helper.hashPassword(request.getParameter("confirmpass"));
        
        
        User user = (User) session.getAttribute("user");
        
        UserDAO dao = new UserDAO();
        User updateUser = dao.getUser(user.getUsername());
        if(updateUser == null){
            response.sendError(500, "Error while changing password");
            return;
        }
        
        if(newPassword.equals(confirmPassword)){
            if(request.getParameter("newpass").length() >= 8){
                if(updateUser.getPassword().equals(currentPassword)){
                    updateUser = new User();
                    updateUser.setPassword(newPassword);
                    updateUser = dao.updateUser(updateUser, user.getUsername());
                    if(updateUser == null){
                        response.sendError(500, "Error while changing password");
                        return;
                    }
                    //keeping log on users account 
                    new HistoryDAO().keepLog(updateUser.getUsername(), "Password changed","Password changed", request.getRemoteAddr());
                }
                else{
                    list.add("Current password did not matched.");
                }
            }
            else{
                list.add("Password length is less than 8 character.");
            }
        }
        else{
            list.add("New and Confirm password did not matched.");
        }
        
        session.setAttribute("user", updateUser);
        manageError(2, list);
        request.setAttribute("errors", this.errors);
        if(list.isEmpty()){
            this.success = " Password changed sucessfully";
        }
        response.sendRedirect(request.getContextPath() + "/profile/editprofile");
    }
    
    private void changeQuestions(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List list = new ArrayList();
        
        String question1,question2,answer1,answer2;
        HttpSession session = request.getSession();
        
        question1 = request.getParameter("question1");
        question2 = request.getParameter("question2");
        answer1 = request.getParameter("answer1");
        answer2 = request.getParameter("answer2");
        
        User updateUser = new User();
                
        if(question1 == null && answer1==null && question2 == null && answer2==null){
            list.add("No question is selected.");
        }
        boolean isEmpty = true;
        if(question1 != null && answer1!=null){
            question1 = question1.trim();
            answer1 = answer1.trim();
            if(question1.equalsIgnoreCase("")){
                list.add("Invalid First Question selection.");
                isEmpty = true;
            }
            else{
                isEmpty=false;
            }
            if(answer1.equalsIgnoreCase("")){
                list.add("Invalid First Question's answer.");
                isEmpty=true;
            }
            else{
                if(isEmpty == false){
                    isEmpty=false;
                }
                else{
                    isEmpty=true;
                }
                
            }
        }
        
        if(!isEmpty){
            updateUser.setQuestion1(question1);
            updateUser.setAnswer1(answer1);
        }
        
        boolean isEmpty2 = true;
        if(question2 != null || answer2!=null){
            question2 = question2.trim();
            answer2 = answer2.trim();
            if(question2.equalsIgnoreCase("")){
                list.add("Invalid Second Question selection.");
                isEmpty2 = true;
            }
            else{
                isEmpty2 = false;
            }
            if(answer2.equalsIgnoreCase("")){
                list.add("Invalid Second Question's answer.");
                isEmpty2 = true;
            }
            else{
                if(isEmpty2 == false){
                    isEmpty2 = false;
                }
                else{
                    isEmpty2 = true;
                }
            }
        }
        
        if(!isEmpty2){
            updateUser.setQuestion2(question2);
            updateUser.setAnswer2(answer2);
        }
                
        User user = (User) session.getAttribute("user");
        UserDAO dao = new UserDAO();
        updateUser = dao.updateUser(updateUser, user.getUsername());
        if(updateUser == null){
            response.sendError(500, "Error While changing question");
            return;
        }
        if(!isEmpty || !isEmpty2){
            //keeping log on users account 
            new HistoryDAO().keepLog(updateUser.getUsername(), "Question changed","Security question changed", request.getRemoteAddr());
        }
        
        session.setAttribute("user",updateUser);
        manageError(3, list);
        request.setAttribute("errors", this.errors);
        if(list.isEmpty()){
            this.success = " Security question updated successfully";
        }
        response.sendRedirect(request.getContextPath() + "/profile/editprofile");
    }
    
    private void viewPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        request.setAttribute("errors", this.errors);
        request.setAttribute("success", this.success);
        this.success = "";
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void doOperation(HttpServletRequest request, HttpServletResponse response, String username, String operation) throws IOException {
        User otherUser = null;

        User loginUser = (User) request.getSession().getAttribute("user");

        switch (operation) {
            case "blockrequest":
                otherUser = new User();
                otherUser.setStatus("blocked");
                otherUser = new UserDAO().updateUser(otherUser, username);
                if (otherUser == null) {
                    response.sendError(500, "Databse Error while fetching user");
                    return;
                }
                //keeping log on admins account
                new HistoryDAO().keepLog(loginUser.getUsername(), "User blocked", "User blocked : " + username, request.getRemoteAddr());

                request.setAttribute("otherUser", otherUser);
                response.sendRedirect(request.getContextPath() + "/profile?user=" + username);
                break;
            case "activerequest":
                otherUser = new User();
                otherUser.setStatus("active");
                otherUser = new UserDAO().updateUser(otherUser, username);
                if (otherUser == null) {
                    response.sendError(500, "Databse Error while fetching user");
                    return;
                }

                //keeping log on admins account
                new HistoryDAO().keepLog(loginUser.getUsername(), "User activated", "User activate : " + username, request.getRemoteAddr());

                request.setAttribute("otherUser", otherUser);
                response.sendRedirect(request.getContextPath() + "/profile?user=" + username);
                break;
            case "deleterequest":
                boolean isDeleted = new UserDAO().deleteUser(username);
                if (isDeleted) {
                    //keeping log on admins account
                    new HistoryDAO().keepLog(loginUser.getUsername(), "User deleted", "User deleted : " + username, request.getRemoteAddr());
                    response.sendRedirect(request.getContextPath() + "/profile");
                    return;

                } else {
                    errors.add("Error while deleting user");
                    request.setAttribute("otherUser", otherUser);
                    request.setAttribute("errors", this.errors);
                    response.sendRedirect(request.getContextPath() + "/profile?user=" + username);
                    return;
                }
            default:
                response.sendError(404, "Page you are searching is not available.");
        }
    }
    
    //only foe edit operations
    private void manageError(int index, List list){
        List empty_list = new ArrayList();
        switch(index){
            case 1:
                this.errors.add(list);
                this.errors.add(empty_list);
                this.errors.add(empty_list);
                break;
            case 2:
                this.errors.add(empty_list);
                this.errors.add(list);
                this.errors.add(empty_list);
                break;
            case 3:
                this.errors.add(empty_list);
                this.errors.add(empty_list);
                this.errors.add(list);
                break;
            default :
                System.out.println("Error:: IndexOut of bound");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Profile Controller Configuration";
    }
}
