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
import java.sql.Statement;
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
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from user where username = '" + username + "'");
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
        try {
            con = Database.getDatabase().getConnection();
            String SQL = "update user set";

            if (updateUser.getUsername() != null) {
                SQL = SQL + " username = '" + updateUser.getUsername() + "'";
            } else {
                SQL = SQL + " username = '" + username + "'";
            }
            if (updateUser.getFirst_name() != null) {
                SQL = SQL + ", first_name = '" + updateUser.getFirst_name() + "'";
            }
            if (updateUser.getLast_name() != null) {
                SQL = SQL + ", last_name = '" + updateUser.getLast_name() + "'";
            }
            if (updateUser.getQuestion1() != null) {
                SQL = SQL + ", question1 = '" + updateUser.getQuestion1() + "'";
            }
            if (updateUser.getQuestion2() != null) {
                SQL = SQL + ", question2 = '" + updateUser.getQuestion2() + "'";
            }
            if (updateUser.getAnswer1() != null) {
                SQL = SQL + ", answer1 = '" + updateUser.getAnswer1() + "'";
            }
            if (updateUser.getAnswer2() != null) {
                SQL = SQL + ", answer2 = '" + updateUser.getAnswer2() + "'";
            }
            if (updateUser.getEmail() != null) {
                SQL = SQL + ", email = '" + updateUser.getEmail() + "'";
            }
            if (updateUser.getBio() != null) {
                SQL = SQL + ", bio = '" + updateUser.getBio() + "'";
            }
            if (updateUser.getDate_of_birth() != null) {
                SQL = SQL + ", date_of_birth = '" + updateUser.getDate_of_birth() + "'";
            }
            if (updateUser.getProfile_picture() != null) {
                SQL = SQL + ", profile_picture = '" + updateUser.getProfile_picture() + "'";
            }
            if (updateUser.getPhone_number() != null) {
                SQL = SQL + ", phone_number = '" + updateUser.getPhone_number() + "'";
            }
            if (updateUser.getPassword() != null) {
                SQL = SQL + ", password = '" + updateUser.getPassword() + "'";
            }
            if (updateUser.getGender() != null) {
                SQL = SQL + ", gender = '" + updateUser.getGender() + "'";
            }
            if (updateUser.getRole() != null) {
                SQL = SQL + ", role = '" + updateUser.getRole() + "'";
            }
            if (updateUser.getStatus() != null) {
                SQL = SQL + ", status = '" + updateUser.getStatus() + "'";
            }

            SQL = SQL + " where username = '" + username + "';";

            PreparedStatement st = con.prepareStatement(SQL);
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
