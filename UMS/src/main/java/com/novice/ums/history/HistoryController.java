/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.history;

import com.novice.ums.model.HistoryDAO;
import com.novice.ums.model.User;
import java.io.IOException;
import java.util.Arrays;
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
@WebServlet(name = "HistoryController", urlPatterns = {"/history/*"})
public class HistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String URI[] = request.getRequestURI().split("/");
        System.out.println(Arrays.toString(URI));

        String username, type, search, page;
        User user = (User) request.getSession().getAttribute("user");

        switch (URI.length) {
            case 3:
                // if no username then show login user history
                username = request.getParameter("user");
                type = request.getParameter("type");
                page = request.getParameter("page");
                System.out.println(username + " " + type + " " + page);
                if (username == null && type == null) {
                    // current logged in user's history
                    if (page == null) {
                        List histories = getHistories(response, user.getUsername(), 1);
                        request.getSession().setAttribute("histories", histories);
                    } else {
                        try {
                            List histories = getHistories(response, user.getUsername(), Integer.parseInt(page));
                            request.getSession().setAttribute("histories", histories);
                        } catch (Exception ex) {
                            response.sendError(404, "Incorrect URL");
                            return;
                        }
                    }
                    int pageno = new HistoryDAO().getNoOfPages(user.getUsername());
                    List types = getColumnTypes(response, user.getUsername());
                    request.getSession().setAttribute("types", types);
                    request.getSession().setAttribute("pageno", pageno);
                    request.setAttribute("mine", "true");

                } else if (username == null && type != null) {
                    // current logged in user's history with differnt type
                    if (page == null) {
                        List histories = getHistories(response, user.getUsername(), type, 1);
                        request.getSession().setAttribute("histories", histories);
                    } else {
                        try {
                            List histories = getHistories(response, user.getUsername(), type, Integer.parseInt(page));
                            request.getSession().setAttribute("histories", histories);
                        } catch (Exception ex) {
                            response.sendError(404, "Incorrect URL");
                            return;
                        }
                    }
                    int pageno = new HistoryDAO().getNoOfPages(user.getUsername(), type);
                    List types = getColumnTypes(response, user.getUsername());
                    request.getSession().setAttribute("types", types);
                    request.getSession().setAttribute("pageno", pageno);
                    request.setAttribute("mine", "true");

                } else if (username != null && type != null) {
                    // others history with type
                    if (user.getRole().equalsIgnoreCase("admin")) {
                        if (page == null) {
                            List histories = getHistories(response, username, type, 1);
                            request.getSession().setAttribute("histories", histories);
                        } else {
                            try {
                                List histories = getHistories(response, username, type, Integer.parseInt(page));
                                request.getSession().setAttribute("histories", histories);
                            } catch (Exception ex) {
                                response.sendError(404, "Incorrect URL");
                                return;
                            }
                        }
                    } else {
                        // access denied
                        response.sendError(403, "Access denied. Client are not permitted.");
                        return;
                    }
                    int pageno = new HistoryDAO().getNoOfPages(username, type);
                    List types = getColumnTypes(response, user.getUsername());
                    request.getSession().setAttribute("types", types);
                    request.getSession().setAttribute("pageno", pageno);
                    request.setAttribute("username", username);

                } else if (username != null && type == null) {
                    //others history
                    if (user.getRole().equalsIgnoreCase("admin")) {
                        if (page == null) {
                            List histories = getHistories(response, username, 1);
                            request.getSession().setAttribute("histories", histories);
                        } else {
                            try {
                                List histories = getHistories(response, username, Integer.parseInt(page));
                                request.getSession().setAttribute("histories", histories);
                            } catch (Exception ex) {
                                response.sendError(404, "Incorrect URL");
                                return;
                            }
                        }
                    } else {
                        // access denied
                        response.sendError(403, "Access denied. Client are not permitted.");
                        return;
                    }
                    int pageno = new HistoryDAO().getNoOfPages(username);
                    List types = getColumnTypes(response, user.getUsername());
                    request.getSession().setAttribute("types", types);
                    request.getSession().setAttribute("pageno", pageno);
                    request.setAttribute("username", username);

                } else {
                    //error 
                    response.sendError(404, "Invalid URL. Please enter a valid URL.");
                    return;
                }
                viewPage(request, response, "/history.jsp");
                break;
            case 4:
                if (URI[3].equals("all")) {
                    if (user.getRole().equalsIgnoreCase("admin")) {
                        search = request.getParameter("hsearch");
                        type = request.getParameter("type");
                        page = request.getParameter("page");
                        System.out.println(search + " " + type + " " + page);
                        if (search == null && type == null) {
                            // all history
                            if (page == null) {
                                List histories = getAllHistories(response, 1);
                                request.getSession().setAttribute("histories", histories);
                            } else {
                                try {
                                    List histories = getAllHistories(response, Integer.parseInt(page));
                                    request.getSession().setAttribute("histories", histories);
                                } catch (Exception ex) {
                                    response.sendError(404, "Incorrect URL");
                                    return;
                                }
                            }
                            int pageno = new HistoryDAO().getAllNoOfPages();
                            List types = getColumnTypes(response);
                            request.getSession().setAttribute("types", types);
                            request.getSession().setAttribute("pageno", pageno);
                            request.setAttribute("all", "true");

                        } else if (search != null && type != null) {
                            //all history with search and type
                            if (page == null) {
                                List histories = getSearchHistories(response, search, type, 1);
                                request.getSession().setAttribute("histories", histories);
                            } else {
                                try {
                                    List histories = getSearchHistories(response, search, type, Integer.parseInt(page));
                                    request.getSession().setAttribute("histories", histories);
                                } catch (Exception ex) {
                                    response.sendError(404, "Incorrect URL");
                                    return;
                                }
                            }
                            int pageno = new HistoryDAO().getNoOfSearchPages(search, type);
                            List types = getColumnTypesOfSearch(response, search);
                            request.getSession().setAttribute("types", types);
                            request.getSession().setAttribute("pageno", pageno);
                            request.setAttribute("all", "true");

                        } else if (search != null && type == null) {
                            // all hsitory with search
                            if (page == null) {
                                List histories = getSearchHistories(response, search, 1);
                                request.getSession().setAttribute("histories", histories);
                            } else {
                                try {
                                    List histories = getSearchHistories(response, search, Integer.parseInt(page));
                                    request.getSession().setAttribute("histories", histories);
                                } catch (Exception ex) {
                                    response.sendError(404, "Incorrect URL");
                                    return;
                                }
                            }
                            int pageno = new HistoryDAO().getNoOfSearchPages(search);
                            List types = getColumnTypesOfSearch(response, search);
                            request.getSession().setAttribute("types", types);
                            request.getSession().setAttribute("pageno", pageno);
                            request.setAttribute("all", "true");

                        } else if (search == null && type != null) {
                            // all history with search
                            if (page == null) {
                                List histories = getAllHistories(response, type, 1);
                                request.getSession().setAttribute("histories", histories);
                            } else {
                                try {
                                    List histories = getAllHistories(response, type, Integer.parseInt(page));
                                    request.getSession().setAttribute("histories", histories);
                                } catch (Exception ex) {
                                    response.sendError(404, "Incorrect URL");
                                    return;
                                }
                            }
                            int pageno = new HistoryDAO().getAllNoOfPages(type);
                            List types = getColumnTypes(response);
                            request.getSession().setAttribute("types", types);
                            request.getSession().setAttribute("pageno", pageno);
                            request.setAttribute("all", "true");

                        } else {
                            //error
                            response.sendError(404, "Invalid URL. Please enter a valid URL.");
                            return;
                        }
                        viewPage(request, response, "/history.jsp");
                        break;
                    } else {
                        // access denied
                        response.sendError(403, "Access denied. Client are not permitted.");
                        return;
                    }
                }
            // else
            //continue to default
            default:
                response.sendError(404, "Invalid URL. Please check the URL.");
                break;
        }
    }

    private void viewPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private List getAllHistories(HttpServletResponse response, int page) throws IOException {
        List histories = new HistoryDAO().getAllHistroy(page);
        // if there is any error in UserDAO  otherUser will be null
        if (histories == null) {
            response.sendError(500, "Databse Error while fetching history");
            return null;
        }
        return histories;
    }

    private List getAllHistories(HttpServletResponse response, String type, int page) throws IOException {
        List histories = new HistoryDAO().getAllHistroy(type, page);
        // if there is any error in UserDAO  otherUser will be null
        if (histories == null) {
            response.sendError(500, "Databse Error while fetching history");
            return null;
        }
        return histories;
    }

    private List getHistories(HttpServletResponse response, String username, int page) throws IOException {
        List histories = new HistoryDAO().getHistroy(username, page);
        // if there is any error in UserDAO  otherUser will be null
        if (histories == null) {
            response.sendError(500, "Databse Error while fetching history");
            return null;
        }
        return histories;
    }

    private List getHistories(HttpServletResponse response, String username, String type, int page) throws IOException {
        List histories = new HistoryDAO().getHistroy(username, type, page);
        // if there is any error in UserDAO  otherUser will be null
        if (histories == null) {
            response.sendError(500, "Databse Error while fetching history");
            return null;
        }
        return histories;
    }

    private List getColumnTypes(HttpServletResponse response, String username) throws IOException {
        List types = new HistoryDAO().getColumnTypesFromRS(username);
        if (types == null) {
            response.sendError(500, "Databse Error while fetching history content");
            return null;
        }
        return types;
    }

    private List getColumnTypes(HttpServletResponse response) throws IOException {
        List types = new HistoryDAO().getColumnTypesFromRS();
        if (types == null) {
            response.sendError(500, "Databse Error while fetching history content");
            return null;
        }
        return types;
    }

    private List getColumnTypesOfSearch(HttpServletResponse response, String search) throws IOException {
        List types = new HistoryDAO().getSearchColumnTypesFromRS(search);
        if (types == null) {
            response.sendError(500, "Databse Error while fetching history content");
            return null;
        }
        return types;
    }

    private List getSearchHistories(HttpServletResponse response, String search, String type, int page) throws IOException {
        List histories = new HistoryDAO().searchHistory(search, type, page);
        // if there is any error in UserDAO  otherUser will be null
        if (histories == null) {
            response.sendError(500, "Databse Error while fetching history");
            return null;
        }
        return histories;
    }

    private List getSearchHistories(HttpServletResponse response, String search, int page) throws IOException {
        List histories = new HistoryDAO().searchHistory(search, page);
        // if there is any error in UserDAO  otherUser will be null
        if (histories == null) {
            response.sendError(500, "Databse Error while fetching history");
            return null;
        }
        return histories;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
