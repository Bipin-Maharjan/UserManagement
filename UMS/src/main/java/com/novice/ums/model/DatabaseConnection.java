/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class DatabaseConnection {
    protected Connection getConnection() throws ClassNotFoundException, SQLException{
        String user ="root";
        String url = "jdbc:mysql://localhost:3308/ums";
        String password = "";

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }
}
