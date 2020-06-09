/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.search;

import com.novice.ums.model.UserDAO;
import java.io.IOException;
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
@WebServlet(name = "Search", urlPatterns = {"/search"})
public class SearchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        String role = request.getParameter("role");
        String page = request.getParameter("page");

        if (search != null && role == null) {
            //search user only
            UserDAO dao = new UserDAO();
            if (page == null) {
                //default
                request.getSession().setAttribute("searchedUsers", dao.searchUser(search, null, 1));
            } else {
                //requested page
                try {
                    request.getSession().setAttribute("searchedUsers", dao.searchUser(search, null, Integer.parseInt(page)));
                } catch (Exception ex) {
                    response.sendError(404, "Incorrect URL");
                    return;
                }
            }
            request.getSession().setAttribute("totalpageno",dao.totalSearchPages(search, null));
        } else if (search != null && role != null) {
            // search and filter user
            UserDAO dao = new UserDAO();
            if (page == null) {
                //default
                request.getSession().setAttribute("searchedUsers", dao.searchUser(search, role, 1));
            } else {
                //requested page
                try {
                    request.getSession().setAttribute("searchedUsers", dao.searchUser(search, role, Integer.parseInt(page)));
                } catch (Exception ex) {
                    response.sendError(404, "Incorrect URL");
                    return;
                }
            }
            request.getSession().setAttribute("totalpageno",dao.totalSearchPages(search, null));
        } else {
            // error no search query
            response.sendError(404, "Page not found for this url request");
            return;
        }
        viewPage(request, response, "/search.jsp");
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

    @Override
    public String getServletInfo() {
        return "Search Controller";
    }

}
