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
 * @author HP All the database manipulation are written in DAO Class
 */
public class UserDAO {

    // Fetch user from database and map the resultset data with object;
    public User getUser(String username) {
        User user = null;
        Connection con = null;
        try {
            con = Database.getDatabase().getConnection();
            PreparedStatement st = con.prepareStatement("Select * from user where username = ?");
            st.setString(1, username.toLowerCase());
            ResultSet rs = st.executeQuery();
            rs.next();
            
            user = new User(rs.getString("username"),rs.getString("password"),rs.getString("first_name"),rs.getString("last_name"),
            rs.getString("role"),rs.getString("email"),rs.getString("phone_number"),rs.getString("date_of_birth"),rs.getString("gender")
            ,rs.getString("question1"),rs.getString("answer1"),rs.getString("question2"),rs.getString("answer2"),rs.getString("profile_picture"),
                    rs.getString("bio"),rs.getString("status"));
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            user = null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    //to update the user
    public User updateUser(User updateUser, String username) {
        Connection con = null;
        List<String> changes = new ArrayList<String>();
        try {
            con = Database.getDatabase().getConnection();
            String sql = "update user set username = ?";
            changes.add("username");
                
            if (updateUser.getFirst_name() != null) {
                sql = sql + ", first_name = ?";
                changes.add("first_name");
            }
            if (updateUser.getLast_name() != null) {
                sql = sql + ", last_name = ?";
                changes.add("last_name");
            }
            if (updateUser.getQuestion1() != null) {
                sql = sql + ", question1 = ?";
                changes.add("question1");
            }
            if (updateUser.getQuestion2() != null) {
                sql = sql + ", question2 = ?";
                changes.add("question2");
            }
            if (updateUser.getAnswer1() != null) {
                sql = sql + ", answer1 = ?";
                changes.add("answer1");
            }
            if (updateUser.getAnswer2() != null) {
                sql = sql + ", answer2 = ?";
                changes.add("answer2");
            }
            if (updateUser.getEmail() != null) {
                sql = sql + ", email = ?";
                changes.add("email");
            }
            if (updateUser.getBio() != null) {
                sql = sql + ", bio = ?";
                changes.add("bio");
            }
            if (updateUser.getDate_of_birth() != null) {
                sql = sql + ", date_of_birth = ?";
                changes.add("date_of_birth");
            }
            if (updateUser.getProfile_picture() != null) {
                sql = sql + ", profile_picture = ?";
                changes.add("profile_picture");
            }
            if (updateUser.getPhone_number() != null) {
                sql = sql + ", phone_number = ?";
                changes.add("phone_number");
            }
            if (updateUser.getPassword() != null) {
                sql = sql + ", password = ?";
                changes.add("password");
            }
            if (updateUser.getGender() != null) {
                sql = sql + ", gender = ?";
                changes.add("gender");
            }
            if (updateUser.getRole() != null) {
                sql = sql + ", role = ?";
                changes.add("role");
            }
            if (updateUser.getStatus() != null) {
                sql = sql + ", status = ?";
                changes.add("status");
            }

            sql = sql + " where username = ? ;";
            changes.add("current_username");

            PreparedStatement st = con.prepareStatement(sql);
            
            for(String change : changes){
                
                switch(change){
                    case "username":
                        if(updateUser.getUsername() == null){
                            st.setString(changes.indexOf(change)+1, username.toLowerCase());
                        }
                        else{
                            st.setString(changes.indexOf(change)+1, updateUser.getUsername().toLowerCase());
                        }
                        break;
                    case "first_name":
                        st.setString(changes.indexOf(change)+1, updateUser.getFirst_name());
                        break;
                    case "last_name":
                        st.setString(changes.indexOf(change)+1, updateUser.getLast_name());
                        break;
                    case "question1":
                        st.setString(changes.indexOf(change)+1, updateUser.getQuestion1());
                        break;
                    case "question2":
                        st.setString(changes.indexOf(change)+1, updateUser.getQuestion2());
                        break;
                    case "answer1":
                        st.setString(changes.indexOf(change)+1, updateUser.getAnswer1());
                        break;
                    case "answer2":
                        st.setString(changes.indexOf(change)+1, updateUser.getAnswer2());
                        break;
                    case "email":
                        st.setString(changes.indexOf(change)+1, updateUser.getEmail());
                        break;
                    case "bio":
                        st.setString(changes.indexOf(change)+1, updateUser.getBio());
                        break;
                    case "date_of_birth":
                        st.setString(changes.indexOf(change)+1, updateUser.getDate_of_birth());
                        break;
                    case "profile_picture":
                        st.setString(changes.indexOf(change)+1, updateUser.getProfile_picture());
                        break;
                    case "phone_number":
                        st.setString(changes.indexOf(change)+1, updateUser.getPhone_number());
                        break;
                    case "password":
                        st.setString(changes.indexOf(change)+1, updateUser.getPassword());
                        break;
                    case "gender":
                        st.setString(changes.indexOf(change)+1, updateUser.getGender());
                        break;
                    case "role":
                        st.setString(changes.indexOf(change)+1, updateUser.getRole());
                        break;
                    case "status":
                        st.setString(changes.indexOf(change)+1, updateUser.getStatus());
                        break;  
                    case "current_username":
                        st.setString(changes.indexOf(change)+1, username);
                        break;  
                }   
            }
            
            st.executeUpdate();
            if(updateUser.getUsername() == null){
                updateUser = getUser(username);
            }
            else{
                updateUser = getUser(updateUser.getUsername());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            updateUser = null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return updateUser;
    }
    
    public boolean deleteUser(String username){
        Connection con = null;
        try {
            con = Database.getDatabase().getConnection();
            PreparedStatement st = con.prepareStatement("Delete from user where username = ?");
            st.setString(1, username.toLowerCase());
            st.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean isUsernameAvailable(String username){
        Connection con=null;
        boolean value = false;
        try {
            con = Database.getDatabase().getConnection();
            PreparedStatement st = con.prepareStatement("select * from user where username = ? ;");
            st.setString(1,username.toLowerCase());
            ResultSet rs = st.executeQuery();
            rs.last();
            value = rs.getRow() == 0? true:false;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            value = false;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }
    
}
