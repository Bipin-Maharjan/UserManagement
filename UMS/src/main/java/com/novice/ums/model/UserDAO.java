/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
        User user = new User();
        Connection con = null;
        try {
            con = Database.getDatabase().getConnection();
            PreparedStatement st = con.prepareStatement("Select * from user where username = ?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            fillUser(user, rs);
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
                            st.setString(changes.indexOf(change)+1, username);
                        }
                        else{
                            st.setString(changes.indexOf(change)+1, updateUser.getUsername());
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
            updateUser = getUser(username);
            
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
            st.setString(1, username);
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

    // Map the result data with objec. Called by getUser function
    private void fillUser(User user, ResultSet rs) throws SQLException {
        List<String> col_name = getColumns(rs);
        rs.next();
        if (col_name.contains("username")) {
            user.setUsername(rs.getString("username"));
        }
        if (col_name.contains("first_name")) {
            user.setFirst_name(rs.getString("first_name"));
        }
        if (col_name.contains("last_name")) {
            user.setLast_name(rs.getString("last_name"));
        }
        if (col_name.contains("question1")) {
            user.setQuestion1(rs.getString("question1"));
        }
        if (col_name.contains("question2")) {
            user.setQuestion2(rs.getString("question2"));
        }
        if (col_name.contains("answer1")) {
            user.setAnswer1(rs.getString("answer1"));
        }
        if (col_name.contains("answer2")) {
            user.setAnswer2(rs.getString("answer2"));
        }
        if (col_name.contains("email")) {
            user.setEmail(rs.getString("email"));
        }
        if (col_name.contains("bio")) {
            user.setBio(rs.getString("bio"));
        }
        if (col_name.contains("date_of_birth")) {
            user.setDate_of_birth(rs.getString("date_of_birth"));
        }
        if (col_name.contains("profile_picture")) {
            user.setProfile_picture(rs.getString("profile_picture"));
        }
        if (col_name.contains("phone_number")) {
            user.setPhone_number(rs.getString("phone_number"));
        }
        if (col_name.contains("password")) {
            user.setPassword(rs.getString("password"));
        }
        if (col_name.contains("gender")) {
            user.setGender(rs.getString("gender"));
        }
        if (col_name.contains("role")) {
            user.setRole(rs.getString("role"));
        }
        if (col_name.contains("status")) {
            user.setStatus(rs.getString("status"));
        }
    }

    // fetch and return the column name of result set data
    private List<String> getColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData data = rs.getMetaData();
        int count = data.getColumnCount();
        List<String> col_name = new ArrayList<String>();
        for (int col = 1; col <= count; col++) {
            col_name.add(data.getColumnName(col));
        }
        if (col_name.isEmpty()) {
            throw new SQLException("No Row Selected");
        }
        return col_name;
    }

}
