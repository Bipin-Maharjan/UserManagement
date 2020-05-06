/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            st.setString(1, username);
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
}
