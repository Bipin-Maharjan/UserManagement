/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.profile;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URI[] = request.getRequestURI().split("/");
        HttpSession session = request.getSession();
        String page = "myprofile.jsp";

        switch (URI.length) {
            case 3:
                //view profile or operation in others profile
                String parameter = request.getParameter("user");
                if (parameter != null) {
                    //operation or view others profile
                    String splitParameter[] = parameter.split("/");
                    String username = splitParameter[0];
                    User otherUser = new UserDAO().getUser(username);
                    // is there is any error in UserDAO  otherUser will be null
                    if (otherUser == null) {
                        response.sendError(500, "Databse Error while fetching user");
                        return;
                    }

                    switch (splitParameter.length) {
                        case 2:
                            User user = (User) session.getAttribute("user");
                            //validating user for admin and not the logged in user's account.
                            if (user.getRole().equalsIgnoreCase("admin")
                                    && !user.getUsername().equalsIgnoreCase(username)) {

                                switch (splitParameter[1]) {
                                    case "blockrequest":
                                        otherUser = new User();
                                        otherUser.setStatus("blocked");
                                        otherUser = new UserDAO().updateUser(otherUser, username);
                                        if (otherUser == null) {
                                            response.sendError(500, "Databse Error while fetching user");
                                            return;
                                        }
                                        //keeping log on admins account
                                        new HistoryDAO().keepLog(user.getUsername(), "block", "User blocked : " + username, request.getRemoteAddr());

                                        request.setAttribute("otherUser", otherUser);
                                        response.sendRedirect(request.getContextPath() + "/profile?u=" + username);
                                        return;
                                    case "activerequest":
                                        otherUser = new User();
                                        otherUser.setStatus("active");
                                        otherUser = new UserDAO().updateUser(otherUser, username);
                                        if (otherUser == null) {
                                            response.sendError(500, "Databse Error while fetching user");
                                            return;
                                        }

                                        //keeping log on admins account
                                        new HistoryDAO().keepLog(user.getUsername(), "activate", "User activate : " + username, request.getRemoteAddr());

                                        request.setAttribute("otherUser", otherUser);
                                        response.sendRedirect(request.getContextPath() + "/profile?u=" + username);
                                        return;
                                    case "deleterequest":
                                        boolean isDeleted = new UserDAO().deleteUser(username);
                                        if (isDeleted) {

                                            //keeping log on admins account
                                            new HistoryDAO().keepLog(user.getUsername(), "delete", "User deleted : " + username, request.getRemoteAddr());

                                            response.sendRedirect("index.html");
                                            return;

                                        } else {
                                            errors.add("Error while deleting user");
                                            request.setAttribute("otherUser", otherUser);
                                            request.setAttribute("errors", this.errors);
                                            response.sendRedirect(request.getContextPath() + "/profile?u=" + username);
                                            return;
                                        }
                                    default:
                                        response.sendError(404, "Page you are searching is not available.");
                                        return;
                                }
                            }
                        //else continue to case 1
                        case 1:
                            //get otheruser
                            request.setAttribute("otherUser", otherUser);
                            page = "othersprofile.jsp";
                            break;
                        default:
                            response.sendError(404, "Page you are searching is not available.");
                            return;
                    }

                }
                request.setAttribute("errors", this.errors);
                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);
                break;
            case 4:
                // Edit your profile
                if (URI[URI.length - 1].equalsIgnoreCase("editprofile")) {
                    response.sendError(503, "This page is currently under construction.");
                    break;
                }
            //else continue to default
            default:
                response.sendError(404, "Page you are searching is not available.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            doGet(request, response);
            return;
        }

        String filename = filepart.getSubmittedFileName().replaceAll("\\s","");
        String splitFilename[] = filename.split("\\.");
        String ext = splitFilename[splitFilename.length-1].trim();

        // checking File type and handeling error
        if (!ext.equalsIgnoreCase("jpg") && !ext.equalsIgnoreCase("png")
                && !ext.equalsIgnoreCase("jpeg") && !ext.equalsIgnoreCase("jifi")) {
            this.errors.add("Image type is not JPG/PNG.");
            doGet(request, response);
            return;
        }
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        //Renaming if same name file exists
        File newImage = new File(folderPath + filename);
        while (newImage.exists()) {
            filename = filename.split("\\.([^.]*)$")[0].trim()+ "1." + ext;
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
        
        response.sendRedirect(request.getContextPath()+"/profile");
    }

    @Override
    public String getServletInfo() {
        return "Profile Controller Configuration";
    }
}
