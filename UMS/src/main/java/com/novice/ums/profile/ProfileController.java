/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.profile;

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

        if (URI.length == 3) {
            String username = request.getParameter("u");
            if (username != null) {
                User otherUser = new UserDAO().getUser(username);
                // is there is any error in UserDAO  otherUser will be null
                if (otherUser == null) {
                    response.sendError(500, "Databse Error while fetching user");
                    return;
                }
                request.setAttribute("otherUser", otherUser);
                page = "othersprofile.jsp";
            }

            request.setAttribute("errors", this.errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
            
        } else if (URI.length >= 4) {
            response.sendError(503, "This page is currently under construction.");
        } else {
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
        
        try{    
            filepart = request.getPart("profile_picture");
        }
        catch(IllegalStateException ex){
            ex.printStackTrace();
            this.errors.add("File size greater than 5MB.");
            doGet(request, response);
            return;
        }
        
        String filename = filepart.getSubmittedFileName();
        String ext = filename.split("\\.")[1].trim();
        
        // checking File type and handeling error
        if(!ext.equalsIgnoreCase("jpg") && !ext.equalsIgnoreCase("png") 
                && !ext.equalsIgnoreCase("jpeg") && !ext.equalsIgnoreCase("jifi")){
            this.errors.add("Image type is not JPG/PNG.");
            doGet(request, response);
            return;
        }
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        //Renaming if same name file exists
        File newImage = new File(folderPath + filename);
        while(newImage.exists()){
            filename = filename.split("\\.")[0]+"1."+ext;
            newImage = new File(folderPath + filename);
        }
        
        //deleting previous profile picture (if any)
        if(user.getProfile_picture() != null){
            File oldImage = new File(folderPath + user.getProfile_picture());
            if(oldImage.exists()){
                oldImage.delete();
            }
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
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Profile Controller Configuration";
    }
}
