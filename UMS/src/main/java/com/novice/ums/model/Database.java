/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 * This Database class is used to get a connection
 */
public class Database {
    private static Database db=null;
    private Connection con;
    
    //this cannot be accessed
    private Database(){}
    
    //use this for creatingobjects
    public static Database getDatabase(){
        if(db == null){
            db = new Database();
            db.setConnection();
            return db;
        }
        else{
            return db;
        }
    }
    
    private void setConnection(){
            String user ="root";
            String url = "jdbc:mysql://localhost:3308/ums";
            String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() throws SQLException {
        if(this.con == null ||  this.con.isClosed()){
            setConnection();
            if(this.con == null){
                throw new SQLException("No connection");
            }
            return this.con;
        }
        else{
            return this.con;
        }
    }
        
}
