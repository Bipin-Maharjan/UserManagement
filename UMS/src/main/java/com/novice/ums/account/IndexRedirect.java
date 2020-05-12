/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
@WebServlet(name = "IndexRedirect", urlPatterns = {""})
public class IndexRedirect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URI[] = request.getRequestURI().split("/");
        switch(URI.length){
            case 2:
                response.sendRedirect(request.getContextPath()+"/account/login");
                break;        
        }
    }

    @Override
    public String getServletInfo() {
        return "Redirect index page to another page";
    }

}
