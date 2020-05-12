/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.filter;

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
 */
@WebFilter(filterName = "AccountsFilter", urlPatterns = {"/account/*"})
public class AccountsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(); 
        
        if(session.getAttribute("user") == null){
            chain.doFilter(request, response);
        }
        else{
            String[] uri = req.getRequestURI().split("/");
            if(!uri[uri.length-1].equals("logout")){
                resp.sendRedirect(req.getContextPath()+"/profile");
            }
            else{
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
    }
    
}
