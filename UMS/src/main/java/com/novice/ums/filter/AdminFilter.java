/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.filter;

import com.novice.ums.model.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 * This filter is for checking if the user is logged in or not and also admin or not.
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/report/*","/adduser/*"})
public class AdminFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(); 
        
        if(session.getAttribute("user") == null){
            resp.sendRedirect(req.getContextPath()+"/account/login");
        }
        else{
            User user =(User) session.getAttribute("user");
            if(user.getRole().equalsIgnoreCase("admin")){
                chain.doFilter(request, response);
            }
            else{
               resp.sendRedirect(req.getContextPath()+"/profile"); 
            }
        }
    }

    @Override
    public void destroy() {}
    
}
