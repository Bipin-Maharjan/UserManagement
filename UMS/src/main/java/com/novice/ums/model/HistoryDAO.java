/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 * All the database manipulation are written in DAO Class
 */
public class HistoryDAO {
    public void keepLog(String username, String type, String remark, String ip_address){
        Connection con = null;
        try {            
            con = Database.getDatabase().getConnection();
            String sql = "Insert into history (username,type,remark,ip_address) values(?,?,?,?);";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username.toLowerCase());
            st.setString(2, type);
            st.setString(3, remark);
            st.setString(4, ip_address);
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getAllNoOfPages(){
        Connection con = null;
        String sql;
        int recordsPerPage = 15;
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from history;";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt("count");
            return (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getAllNoOfPages(String type){
        Connection con = null;
        String sql;
        int recordsPerPage = 15;
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from history where type= ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, type);
            ResultSet rs = st.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt("count");
            return (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getNoOfPages(String username){
        Connection con = null;
        String sql;
        int recordsPerPage = 15;
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from history where username = ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt("count");
            return (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getNoOfPages(String username,String type){
        Connection con = null;
        String sql;
        int recordsPerPage = 15;
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from history where username = ? and type= ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, type);
            ResultSet rs = st.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt("count");
            return (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getNoOfSearchPages(String search, String type) {
        Connection con = null;
        String sql;
        int recordsPerPage = 15;
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from history where username like ? and type= ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, search+"%");
            st.setString(2, type);
            ResultSet rs = st.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt("count");
            return (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getNoOfSearchPages(String search) {
        Connection con = null;
        String sql;
        int recordsPerPage = 15;
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from history where username like ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, search+"%");
            ResultSet rs = st.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt("count");
            return (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List getAllHistroy(int page){
        String sql;
        Connection con=null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page-1);
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history order by date_time desc limit ?,?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, skip);
            st.setInt(2, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public List getAllHistroy(String type,int page){
        String sql;
        Connection con=null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page-1);
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where type = ? order by date_time desc limit ?,?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, type);
            st.setInt(2, skip);
            st.setInt(3, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public List getHistroy(String username,int page){
        String sql;
        Connection con=null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page-1);
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where username = ? order by date_time desc limit ?,?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            st.setInt(2, skip);
            st.setInt(3, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public List getHistroy(String username,String type, int page){
        String sql;
        Connection con=null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page-1);
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where username = ? and type = ? order by date_time desc limit ?,?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, type);
            st.setInt(3, skip);
            st.setInt(4, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public List searchHistory(String searchquery,int page){
        String sql;
        Connection con=null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page-1);
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where username like ? order by date_time desc limit ?,?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, searchquery+"%");
            st.setInt(2, skip);
            st.setInt(3, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public List searchHistory(String searchquery,String type, int page){
        String sql;
        Connection con=null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page-1);
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where username like ? and type = ? order by date_time desc limit ?,?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, searchquery+"%");
            st.setString(2, type);
            st.setInt(3, skip);
            st.setInt(4, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public List getColumnTypesFromRS() {
        String sql;
        Connection con=null;
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select distinct(type) from history;";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List types = new ArrayList();
            while(rs.next()){
                types.add(rs.getString("type"));
            }
            return types;
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List getColumnTypesFromRS(String username) {
        String sql;
        Connection con=null;
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select distinct(type) from history where username = ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            List types = new ArrayList();
            while(rs.next()){
                types.add(rs.getString("type"));
            }
            return types;
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List getSearchColumnTypesFromRS(String search) {
        String sql;
        Connection con=null;
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select distinct(type) from history where username like ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, search+"%");
            ResultSet rs = st.executeQuery();
            List types = new ArrayList();
            while(rs.next()){
                types.add(rs.getString("type"));
            }
            return types;
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private List convertRSToHistory(ResultSet rs) throws SQLException{
        List histories = new ArrayList();
        while(rs.next()){
            histories.add(new History(rs.getString("username"),rs.getString("date_time"),rs.getString("type"),rs.getString("remark"),rs.getString("ip_address")));
        }
        return histories;
    }    
    
}
