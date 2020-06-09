/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.adduser;

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

/**
 *
 * @author HP
 */
@WebServlet(name = "AddUserServelet", urlPatterns = {"/adduser"})
public class AddUserServelet extends HttpServlet {
    
    List errors = new ArrayList();
    String success = "";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        viewPage(request, response, "/addUser.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstname, lastname, username, email, password, confpassword, phone_number, role,
                gender, date_of_birth, question1, question2, answer1, answer2;
        boolean hasError = false;
        UserDAO dao;
        User user,logged_in_user;
        
        logged_in_user = (User)request.getSession().getAttribute("user");

        firstname = request.getParameter("first_name").trim();
        lastname = request.getParameter("last_name").trim();
        username = request.getParameter("username").trim();
        email = request.getParameter("email").trim();
        password = request.getParameter("password").trim();
        confpassword = request.getParameter("confirmpass").trim();
        phone_number = request.getParameter("phone_number").trim();
        role = request.getParameter("role").trim();
        gender = request.getParameter("gender").trim();
        date_of_birth = request.getParameter("date_of_birth").trim();
        question1 = request.getParameter("question1").trim();
        question2 = request.getParameter("question2").trim();
        answer1 = request.getParameter("answer1").trim();
        answer2 = request.getParameter("answer2").trim();

        dao = new UserDAO();
        user = new User();

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
        if (role.equalsIgnoreCase("")) {
            hasError = true;
            this.errors.add("Role is empty.");
        } else {
            user.setRole(role);
        }

        if (!hasError) {
            if (dao.createUser(user)) {
                new HistoryDAO().keepLog(logged_in_user.getUsername(), "User added by Admin",
                        "'"+user.getUsername()+"' added by admin with role '"+role+"'.", request.getRemoteAddr());
                this.success = "User Successfully added.";
                response.sendRedirect(request.getContextPath() + "/adduser");
            } else {
                response.sendError(500, "Error While Creating a User.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/adduser");
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
    
    @Override
    public String getServletInfo() {
        return "This Servlet is used to add the user and is only accessible for admin";
    }

}
