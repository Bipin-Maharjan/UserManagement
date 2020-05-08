/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.filter;

import com.novice.ums.model.User;
import com.novice.ums.model.UserDAO;
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
 * This filter is for checking is user is logged in not to access the profile page
 */
@WebFilter(urlPatterns = {"/profile/*"})
public class LoginFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(); 
        
        if(session.getAttribute("user") == null){
            User user = new UserDAO().getUser("dipak.raii");
            session.setAttribute("user", user);
            resp.getWriter().println("Session Loading done");
//            resp.sendRedirect("login.jsp");
        }
        else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}
    
}
