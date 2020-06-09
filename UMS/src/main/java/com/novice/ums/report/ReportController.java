/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.report;

import com.novice.ums.model.History;
import com.novice.ums.model.HistoryDAO;
import com.novice.ums.model.User;
import com.novice.ums.model.UserDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
@WebServlet(name = "ReportController", urlPatterns = {"/report/*"})
public class ReportController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URI[] = request.getRequestURI().split("/");

        switch (URI.length) {
            case 3:
                viewPage(request, response, "/report.jsp");
                break;
            case 4:
                String start;
                String end;
                switch (URI[3]) {
                    case "history":
                        //get and view some portion of history
                        List<History> histories = new HistoryDAO().getAllHistroy(1);
                        request.setAttribute("histories", histories);
                        viewPage(request, response, "/report.jsp");
                        break;
                    case "clients":
                        //some portion get and view some portion of clients
                        List<User> clientUsers = new UserDAO().fetchUser(1, "client", "asc");
                        request.setAttribute("clientUsers", clientUsers);
                        viewPage(request, response, "/report.jsp");
                        break;
                    case "admins":
                        //some portion get and view some portion of admins
                        List<User> adminUsers = new UserDAO().fetchUser(1, "admin", "asc");
                        request.setAttribute("adminUsers", adminUsers);
                        viewPage(request, response, "/report.jsp");
                        break;
                    case "usercreated":
                        //User created check for date range
                        start = request.getParameter("start");
                        end = request.getParameter("end");
                        if (start != null && end != null) {
                            //get data of selected date range
                            request.setAttribute("usersCreated", getRegisteredUser(start, end));
                            viewPage(request, response, "/report.jsp");
                        } else {
                            // Show Empty
                            request.setAttribute("usersCreated", "empty");
                            viewPage(request, response, "/report.jsp");
                        }
                        break;
                    case "userblocked":
                        //User blocked check for date range
                        start = request.getParameter("start");
                        end = request.getParameter("end");
                        if (start != null && end != null) {
                            //get data of selected date range
                            request.setAttribute("usersBlocked", getBlockedUser(start, end));
                            viewPage(request, response, "/report.jsp");
                        } else {
                            // Show Empty
                            request.setAttribute("usersBlocked", "empty");
                            viewPage(request, response, "/report.jsp");
                        }
                        break;
                    case "online":
                        //get online user and show online user
                        List data = getOnlineUser(1,"asc");
                        request.setAttribute("onlineUsers", data.get(0));
                        viewPage(request, response, "/report.jsp");
                        break;
                    default:
                        //Invalid url
                        response.sendError(404, "Invalid URL. Please enter a valid URL.");
                        break;
                }
                break;
            case 5:
                if (URI[4].equalsIgnoreCase("all")) {
                    String page = request.getParameter("page");
                    String sort = request.getParameter("sort")==null?"asc":request.getParameter("sort");
                    String role =""; 
                    switch (URI[3]) {
                        case "clients":
                            role = "client";
                            if (page != null) {
                                try {
                                    List<User> clientUsers = new UserDAO().fetchUser(Integer.parseInt(page), role , sort);
                                    request.setAttribute("report_users", clientUsers);
                                } catch (Exception ex) {
                                    response.sendError(404, "Incorrect URL");
                                    return;
                                }
                            } else {
                                List<User> clientUsers = new UserDAO().fetchUser(1, role , sort);
                                request.setAttribute("report_users", clientUsers);
                            }
                            String[] client_column = {"Name","Username","Email","Contact No"};
                            request.setAttribute("column", client_column);
                            request.setAttribute("data", role);
                            request.setAttribute("pageno", new UserDAO().getPagesNo(role));
                            viewPage(request, response, "/viewall.jsp");
                            break;
                        case "admins":
                            role = "admin";
                            if (page != null) {
                                try {
                                    List<User> adminUsers = new UserDAO().fetchUser(Integer.parseInt(page), role, sort);
                                    request.setAttribute("report_users", adminUsers);
                                } catch (Exception ex) {
                                    response.sendError(404, "Incorrect URL");
                                    return;
                                }
                            } else {
                                List<User> adminUsers = new UserDAO().fetchUser(1, role, sort);
                                request.setAttribute("report_users", adminUsers);
                            }
                            String[] admin_column = {"Name","Username","Email","Contact No"};
                            request.setAttribute("column", admin_column);
                            request.setAttribute("data", role);
                            request.setAttribute("pageno", new UserDAO().getPagesNo(role));
                            viewPage(request, response, "/viewall.jsp");
                            break;
                        case "online":
                            List data;
                            if (page != null) {
                                try {
                                    data = getOnlineUser(Integer.parseInt(page),sort);
                                    request.setAttribute("report_users", data.get(0));
                                    request.setAttribute("pageno", data.get(1));
                                } catch (Exception ex) {
                                    response.sendError(404, "Incorrect URL");
                                    return;
                                }
                            } else {
                                data = getOnlineUser(1,sort);
                                request.setAttribute("report_users", data.get(0));
                                request.setAttribute("pageno", data.get(1));
                            }
                            String[] online_column = {"Name","Username","Email","Contact No"};
                            request.setAttribute("column", online_column);
                            request.setAttribute("data", "online");
                            viewPage(request, response, "/viewall.jsp");
                            break;
                        default:
                            response.sendError(404, "Invalid URL. Please enter a valid URL.");
                            break;
                    }
                    return;
                }
            //else 
            //continue to default
            default:
                //Invalid url
                response.sendError(404, "Invalid URL. Please enter a valid URL.");
                break;
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
     * This function is for fetching all the online users through their username.
     * This function will return list containing online users list and total page number.
     */
    private List getOnlineUser(int page, String sort) {
        User user;
        int recordsPerPage = 15;
        LinkedHashMap<String, String> details = new HistoryDAO().getOnlineUser(page, sort);
        UserDAO dao = new UserDAO();
        List<User> onlineUsers = new ArrayList<>();
        List data = new ArrayList();
        
        for (String username : details.keySet()) {
            user = dao.getUser(username);
            user.setExtra_info(details.get(username));
            onlineUsers.add(user);
        }
        data.add(onlineUsers);
        data.add((int) Math.ceil(details.size() * 1.0 / recordsPerPage));
        
        return data;
    }

    /**
     * This function will take register user history record and fetch
     * corresponding register user
     */
    private List<User> getRegisteredUser(String start, String end) {
        List<User> registeredUser = new ArrayList();
        List<History> histories = new HistoryDAO().getDateRangeData(start, end, "User Registered");
        UserDAO dao = new UserDAO();
        User user;

        for (History history : histories) {
            user = dao.getUser(history.getUsername());
            user.setExtra_info(history.getDate_time());
            registeredUser.add(user);
        }

        return registeredUser;
    }

    /**
     * This function will take blocked user history record and fetch
     * corresponding blocked user
     */
    private List<User> getBlockedUser(String start, String end) {
        List<User> blockedUser = new ArrayList();
        String blockedUsername = "";
        User user;
        UserDAO dao;

        dao = new UserDAO();
        List<History> histories = new HistoryDAO().getDateRangeData(start, end, "User blocked");
        for (History history : histories) {
            blockedUsername = history.getRemark().split(" ")[3];
            user = dao.getUser(blockedUsername);
            user.setExtra_info(history.getDate_time());
            blockedUser.add(user);
        }

        return blockedUser;
    }

    @Override
    public String getServletInfo() {
        return "Report Controller";
    }

}
