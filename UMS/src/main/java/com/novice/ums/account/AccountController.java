/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.account;

import com.novice.ums.helper.Helper;
import com.novice.ums.model.HistoryDAO;
import com.novice.ums.model.User;
import com.novice.ums.model.UserDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet(name = "AccountController", urlPatterns = {"/account/*"})
public class AccountController extends HttpServlet {

    List errors = new ArrayList();
    String success = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URI[] = request.getRequestURI().split("/");
        switch (URI.length) {
            case 3:
                response.sendRedirect(request.getContextPath()+"/account/login");
                break;
            case 4:
                switch (URI[3]) {
                    case "login":
                        viewPage(request,response,"/login.jsp");
                        break;
                    case "logout":
                        accountLogout(request, response);
                        break;
                    case "register":
                        viewPage(request, response, "/register.jsp");
                        break;
                    case "forgetpassword":
                        viewPage(request,response,"/forgetPasswordUsername.jsp");
                        break;
                    case "verrifyquestions":
                        viewPage(request,response,"/forgetPasswordQuestions.jsp");
                        break;
                    case "setnewpassword":
                        viewPage(request,response,"/forgetPasswordNewpassword.jsp");
                        break;
                    default:
                        response.sendError(404, "The page you are trying get is not Found.");
                }
                break;
            default:
                response.sendError(404, "The page you are trying get is not Found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URI[] = request.getRequestURI().split("/");
        switch(URI.length){
            case 4:
                switch(URI[3]){
                    case "login":
                        accountLogin(request, response);
                        break;
                    case "register":
                        accountRegister(request, response);
                        break;
                    case "checkusername":
                        forgetPasswordUsername(request,response);
                        break;
                    case "checkquestions":
                        forgetPasswordQuestion(request,response);
                        break;
                    case "setnewpassword":
                        forgetPasswordNewpass(request,response);
                        break;
                }
                break;
            default :
                this.errors.add("Invalid Request");
                response.sendRedirect(request.getContextPath() + "/account/login");
        }
    }
    
    /**
     * Function for forwarding dispatcher request for another page.
     * 
     * @param request Request variable
     * @param response Response Variable
     * @param page JSP Page name to redirect
     * @throws ServletException
     * @throws IOException 
     */
    private void viewPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        request.setAttribute("errors", this.errors);
        request.setAttribute("success",this.success);
        this.success = "";
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    /**
     * Function to check the login credentials with database credentials and login the user.
     * 
     * @param request parameter: username, password
     * @param response profile.jsp
     * @throws IOException 
     */
    private void accountLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username, password,result;
        
        username = request.getParameter("username").trim();
        password = request.getParameter("password").trim();
        
        result = new AccountFunctions().verrifyLogin(username, password);
        
        if (result.equalsIgnoreCase("success")) {
            User user = new UserDAO().getUser(username);
            request.getSession().setAttribute("user", user);
            new HistoryDAO().keepLog(user.getUsername(), "Login", "Logged in", request.getRemoteAddr());
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
        else{
            this.errors.add(result);
            response.sendRedirect(request.getContextPath() + "/account/login");
        }
    }

    /**
     * Function to make a user logout.
     * 
     * @param request parameter: none
     * @param response login.jsp
     * @throws IOException 
     */
    private void accountLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            session.removeAttribute("user");
            session.invalidate();
            new HistoryDAO().keepLog(user.getUsername(), "Logout", "Logged out", request.getRemoteAddr());
        }
        response.sendRedirect(request.getContextPath() + "/account/login");
    }
    
    /**
     * Function for adding new client user with validations. 
     * 
     * @param request parameter: firstName, lastName, username, email, password, confirmPassword, phone_number, role,
                gender, date_of_birth, question1, question2, answer1, answer2
     * @param response login.jsp
     * @throws IOException 
     */
    private void accountRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstname, lastname, username, email, password, confpassword, phone_number, role,
                gender, date_of_birth, question1, question2, answer1, answer2;

        firstname = request.getParameter("fname").trim();
        lastname = request.getParameter("lname").trim();
        username = request.getParameter("username").trim();
        email = request.getParameter("email").trim();
        password = request.getParameter("password").trim();
        confpassword = request.getParameter("confpassword").trim();
        phone_number = request.getParameter("phonenumber").trim();
        role = "client";
        gender = request.getParameter("gender").trim();
        date_of_birth = request.getParameter("dob").trim();
        question1 = request.getParameter("question1").trim();
        question2 = request.getParameter("question2").trim();
        answer1 = request.getParameter("answer1").trim();
        answer2 = request.getParameter("answer2").trim();
        
        if (!password.equals(confpassword)) {
            this.errors.add("New and Confirm password did not matched.");
            response.sendRedirect(request.getContextPath() + "/account/register");
            return;
        }
        
        User registerUser = new User(username, password, firstname, lastname, role,
                email, phone_number, date_of_birth, gender, question1, answer1, question2, answer2, null, null, "active");
        
        Object result = new AccountFunctions().verrifyRegister(registerUser);
        
        if(result.getClass().equals("".getClass())){
            if(result.toString().equalsIgnoreCase("success")){
                new HistoryDAO().keepLog(registerUser.getUsername(), "User Register", "Registered to the system", request.getRemoteAddr());
                this.success = "User Registered. Please login to continue.";
                response.sendRedirect(request.getContextPath() + "/account/login");
            }
        }
        else{
            List errorList = (List) result;
            if(errorList.get(errorList.size()-1).equals("sendError")){
                response.sendError((int)errorList.get(0), errorList.get(1).toString());
            }
            else{
                errorList.remove(errorList.size()-1);
                this.errors = errorList;
                response.sendRedirect(request.getContextPath() + "/account/register");
            }
        }
    }

    /**
     * Function to check username to reset password.
     * @param request parameter: username
     * @param response forgetPasswordQuestions.jsp
     * @throws ServletException
     * @throws IOException 
     */
    private void forgetPasswordUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username;
        UserDAO dao;
        User user;
        
        username = request.getParameter("username");
        dao = new UserDAO();
        
        if(!dao.isUsernameAvailable(username)){
            user = dao.getUser(username);
            if (user == null) {
                response.sendError(500, "Databse Error while verrifying user");
                return;
            }
            request.getSession().setAttribute("recoverUser", user);
            response.sendRedirect(request.getContextPath()+"/account/verrifyquestions");
        }
        else{
            this.errors.add("User is not register.");
            request.setAttribute("errors", this.errors);
            response.sendRedirect(request.getContextPath()+"/account/forgetpassword");
        }
    }
    
    /**
     * Function to check the security questions for a user.
     * @param request parameter: answer1, answer2, username
     * @param response forgetPasswordNewpassword.jsp
     * @throws IOException
     * @throws ServletException 
     */
    private void forgetPasswordQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String answer1, answer2,username;
        User user;
        answer1 = request.getParameter("answer1");
        answer2 = request.getParameter("answer2");
        username = request.getParameter("username");
        
        if(username != null){
            user = new UserDAO().getUser(username);
            if (user == null) {
                response.sendError(500, "Databse Error while verrifying answers");
                return;
            }
            request.getSession().setAttribute("recoverUser", user);
        }
        else{
            this.errors.add("Invalid forget password request.");
            request.setAttribute("errors", this.errors);
            response.sendRedirect(request.getContextPath()+"/account/login");
            return;
        }

        if(user.getAnswer1().trim().equalsIgnoreCase(answer1) && user.getAnswer2().trim().equalsIgnoreCase(answer2)){
            response.sendRedirect(request.getContextPath()+"/account/setnewpassword");
        }
        else{
            this.errors.add("Incorrect answer to the questions.");
            request.setAttribute("errors", this.errors);
            response.sendRedirect(request.getContextPath()+"/account/verrifyquestions");
        }
 
    }

    /**
     * Function to reset the password.
     * @param request parameter: password, confirmPassword, username
     * @param response login.jsp
     * @throws IOException
     * @throws ServletException 
     */
    private void forgetPasswordNewpass(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String password, confirmPassword,username;
        UserDAO dao;
        User user;
        password = request.getParameter("password");
        confirmPassword = request.getParameter("confpassword");
        username = request.getParameter("username");
        
        dao = new UserDAO();
        user = dao.getUser(username);
        if (user == null) {
            response.sendError(500, "Databse Error while Changing password");
            return;
        }
        
        if(password.equals(confirmPassword) && !password.trim().equals("")){
            if(password.length() >=8){
                password = Helper.hashPassword(password);
                User updateUser = new User();
                updateUser.setPassword(password);
                updateUser = dao.updateUser(updateUser, user.getUsername());
                if(updateUser == null){
                    response.sendError(500, "Databse Error while Changing password");
                    return;
                }
                new HistoryDAO().keepLog(user.getUsername(), "Password changed", "Password changed via forget password", request.getRemoteAddr());
                if(this.errors.isEmpty()){
                    this.success = "Password Changed Successfully";
                }
                response.sendRedirect(request.getContextPath()+"/account/login");
            }
            else{
                this.errors.add("Password length is less than 8.");
                request.getSession().setAttribute("recoverUser", user);
                request.setAttribute("errors", this.errors);
                response.sendRedirect(request.getContextPath()+"/account/setnewpassword");
            }
        }
        else{
            this.errors.add("Password and Confirm password did not matched.");
            this.errors.add("Please try again.");
            request.getSession().setAttribute("recoverUser", user);
            request.setAttribute("errors", this.errors);
            response.sendRedirect(request.getContextPath()+"/account/setnewpassword");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Account Controller";
    }
    
}
