/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.account;

import com.novice.ums.helper.Helper;
import com.novice.ums.model.User;
import com.novice.ums.model.UserDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class AccountFunctions {
    /**
     * function to verify login
     * @param username
     * @param password
     * @return String
     */
    public String verrifyLogin(String username, String password) {
        String hashPassword;
        UserDAO dao = new UserDAO();
        
        hashPassword = Helper.hashPassword(password);
        
        if (dao.isUsernameAvailable(username)) {
            return "User is not registerd";
        }

        User user = dao.getUser(username);
        if(user.getStatus().equalsIgnoreCase("active")){
            if (user.getPassword().equals(hashPassword)) {
                return "success";
            } else {
                return "Incorrect Username or Password";
            }
        }
        else{
            return "User is blocked. Please contact the Admin.";  
        }
    }
    
    /**
     * Function for verifying and creating user in database user details before sign up.
     * @param registerUser User Object with all the details
     * @return Object of list if error and string "success" if user is created.
     */
    public Object verrifyRegister(User registerUser){
        UserDAO dao = new UserDAO();
        User user = new User();
        boolean hasError = false;
        List errors = new ArrayList();

        if (registerUser.getFirst_name().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Firstname is empty.");
        } else {
            user.setFirst_name(registerUser.getFirst_name());
        }
        if (registerUser.getLast_name().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Lastname is empty.");
        } else {
            user.setLast_name(registerUser.getLast_name());
        }
        if (registerUser.getUsername().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Username is empty.");
        } else {
            if (dao.isUsernameAvailable(registerUser.getUsername())) {
                user.setUsername(registerUser.getUsername());
            } else {
                hasError = true;
                errors.add("Username already Taken.");
            }
        }
        if (registerUser.getEmail().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Email is empty.");
        } else {
            user.setEmail(registerUser.getEmail());
        }
        if (registerUser.getPassword().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Password is empty.");
        } else {
            if (registerUser.getPassword().length() >= 8) {
                user.setPassword(Helper.hashPassword(registerUser.getPassword()));
            } else {
                hasError = true;
                errors.add("Password length is less than 8 character.");
            }
        }
        if (registerUser.getPhone_number().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Phone number is empty.");
        } else {
            if (registerUser.getPhone_number().matches("^(\\+977|[0-9])[0-9]{9,10}$")
                    && (registerUser.getPhone_number().length() == 10 || registerUser.getPhone_number().length() == 14)) {
                user.setPhone_number(registerUser.getPhone_number());
            } else {
                hasError = true;
                errors.add("Invalid phone number");
            }
        }
        if (registerUser.getGender().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Gender is empty.");
        } else {
            user.setGender(registerUser.getGender());
        }
        if (registerUser.getDate_of_birth().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Date of birth is empty.");
        } else {
            user.setDate_of_birth(registerUser.getDate_of_birth());
        }
        if (registerUser.getQuestion1().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Security question 1 is empty.");
        } else {
            user.setQuestion1(registerUser.getQuestion1());
        }
        if (registerUser.getQuestion2().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Security question 2 is empty.");
        } else {
            user.setQuestion2(registerUser.getQuestion2());
        }
        if (registerUser.getAnswer1().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Answer is empty.");
        } else {
            user.setAnswer1(registerUser.getAnswer1());
        }
        if (registerUser.getAnswer2().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Answer is empty.");
        } else {
            user.setAnswer2(registerUser.getAnswer2());
        }
        if (registerUser.getRole().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Role is empty.");
        } else {
            user.setRole(registerUser.getRole());
        }
        if (registerUser.getStatus().equalsIgnoreCase("")) {
            hasError = true;
            errors.add("Status is empty.");
        } else {
            user.setStatus(registerUser.getStatus());
        }

        if (!hasError) {
            if (dao.createUser(user)) {
                return "success";
            } else {
                errors.clear();
                errors.add(500);
                errors.add("Error While Creating a User.");
                errors.add("sendError");
                return errors;
            }
        } else {
            errors.add("sendRedirect");
            return errors;
        }
    }
    
}
