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
                this.errors.add("Invalid Request2");
                response.sendRedirect(request.getContextPath() + "account/login");
        }
    }
    
    private void viewPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        request.setAttribute("errors", this.errors);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void accountLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username, password, hashPassword;
        
        username = request.getParameter("username").trim();
        password = request.getParameter("password").trim();
        hashPassword = Helper.hashPassword(password);
        
        UserDAO dao = new UserDAO();
        if (dao.isUsernameAvailable(username)) {
            this.errors.add("User is not registerd");
            response.sendRedirect(request.getContextPath() + "/account/login");
            return;
        }

        User user = dao.getUser(username);
        if (user.getPassword().equals(hashPassword)) {
            request.getSession().setAttribute("user", user);
            new HistoryDAO().keepLog(user.getUsername(), "Login", "Logged in", request.getRemoteAddr());
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            this.errors.add("Incorrect Username or Password");
            response.sendRedirect(request.getContextPath() + "/account/login");
        }
    }

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

    private void accountRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstname, lastname, username, email, password, confpassword, phone_number, role,
                gender, date_of_birth, question1, question2, answer1, answer2;
        boolean hasError = false;
        UserDAO dao;
        User user;

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

        dao = new UserDAO();
        user = new User();

        user.setRole(role);
        if (firstname.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Firstname is empty.");
        } else {
            user.setFirst_name(firstname);
        }
        if (lastname.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Lastname is empty.");
        } else {
            user.setLast_name(lastname);
        }
        if (username.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Username is empty.");
        } else {
            if (dao.isUsernameAvailable(username)) {
                user.setUsername(username);
            } else {
                hasError = true;
                this.errors.add("Username already Taken.");
            }
        }
        if (email.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Email is empty.");
        } else {
            user.setEmail(email);
        }
        if (password.equalsIgnoreCase("") && confpassword.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Password is empty.");
        } else {
            if (password.equals(confpassword)) {
                if (password.length() >= 8) {
                    user.setPassword(Helper.hashPassword(password));
                } else {
                    hasError = true;
                    this.errors.add("Password length is less than 8 character.");
                }
            } else {
                hasError = true;
                this.errors.add("New and Confirm password did not matched.");
            }
        }
        if (phone_number.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Phone number is empty.");
        } else {
            if (phone_number.matches("^(\\+977|[0-9])[0-9]{9,10}$")
                    && (phone_number.length() == 10 || phone_number.length() == 14)) {
                user.setPhone_number(phone_number);
            } else {
                hasError = true;
                this.errors.add("Invalid phone number");
            }
        }
        if (gender.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Gender is empty.");
        } else {
            user.setGender(gender);
        }
        if (date_of_birth.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Date of birth is empty.");
        } else {
            user.setDate_of_birth(date_of_birth);
        }
        if (question1.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Security question 1 is empty.");
        } else {
            user.setQuestion1(question1);
        }
        if (question2.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Security question 2 is empty.");
        } else {
            user.setQuestion2(question2);
        }
        if (answer1.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Answer is empty.");
        } else {
            user.setAnswer1(answer1);
        }
        if (answer2.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Answer is empty.");
        } else {
            user.setAnswer2(answer2);
        }

        if (!hasError) {
            if (dao.createUser(user)) {
                new HistoryDAO().keepLog(user.getUsername(), "Register", "Registered to the system", request.getRemoteAddr());
                response.sendRedirect(request.getContextPath() + "/account/login");
            } else {
                response.sendError(500, "Error While Creating a User.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/account/register");
        }

    }

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
