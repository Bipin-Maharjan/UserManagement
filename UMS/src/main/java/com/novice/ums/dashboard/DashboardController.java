/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.dashboard;

import com.novice.ums.model.History;
import com.novice.ums.model.HistoryDAO;
import com.novice.ums.model.User;
import com.novice.ums.model.UserDAO;
import java.io.IOException;
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
@WebServlet(name = "DashboardController", urlPatterns = {"/dashboard/*"})
public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        
        if(user.getRole().equalsIgnoreCase("admin")){
            adminDashboard(request);
            viewPage(request, response, "/adminDashboard.jsp");
        }
        else{
            clientDashboard(request,response);
            viewPage(request, response, "/adminDashboard.jsp");
        }
    }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       response.sendError(404, "Page not found for post request");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    /**
     * Function to gather the required date for admin dashboard.
     * @param request 
     */
    private void adminDashboard(HttpServletRequest request) {
        UserDAO userDao = new UserDAO();
        HistoryDAO historyDao = new HistoryDAO();
        int totalClients = userDao.totalUserCount("client");
        int totalAdmins = userDao.totalUserCount("admin");
        int newUser = historyDao.getNewUserCount(7);
        int onlineUser = historyDao.getOnlineUserCount();
        List<History> lastVisitors = historyDao.getLastVisitors(5);
        List<History> mostActiveUser = historyDao.getMostActiveUser();
        request.setAttribute("totalClients", totalClients);
        request.setAttribute("totalAdmins", totalAdmins);
        request.setAttribute("newUser", newUser);
        request.setAttribute("onlineUser", onlineUser);
        request.setAttribute("lastVisitors", lastVisitors);
        request.setAttribute("mostActiveUser", mostActiveUser);
    }

    private void clientDashboard(HttpServletRequest request, HttpServletResponse response) {
        
    }
    
    @Override
    public String getServletInfo() {
        return "Dashboard Controller";
    }

}