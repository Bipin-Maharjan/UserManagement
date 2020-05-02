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
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(url, user, password);
        }
        catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
        
}
