/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.profile;

import com.novice.ums.model.User;
import java.io.IOException;
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
 * Controller class is only used for handling the user interaction / user input and redirect to respective class
 * VIEW: Is a jsp page and is only a UI page
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profile/*"})
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URI [] = request.getRequestURI().split("/");
        String errors [] = new String[0];
        HttpSession session = request.getSession();
        
        //test variable
        User user = null;

        if(URI.length == 3){
            user = new User();
            session.setAttribute("user", user);
            
            request.setAttribute("errors", errors);
            request.setAttribute("isme",true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
            dispatcher.forward(request, response);
        }
        else if(URI.length == 4){
            response.sendError(503, "This page is currently under construction.");
        }
        else{
            response.sendError(404, "Page you are searching is not available.");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Profile Controller Configuration";
    }

}
