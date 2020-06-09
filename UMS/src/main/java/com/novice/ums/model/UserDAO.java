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

    /**
     * Function to fetch user from database
     * @param username user username
     * @return User Object
     */
    public User getUser(String username) {
        User user = null;
        Connection con = null;
        try {
            con = Database.getDatabase().getConnection();
            PreparedStatement st = con.prepareStatement("Select * from user where username = ?");
            st.setString(1, username.toLowerCase());
            ResultSet rs = st.executeQuery();
            rs.next();

            user = new User(rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("role"), rs.getString("email"), rs.getString("phone_number"), rs.getString("date_of_birth"), rs.getString("gender"),
                     rs.getString("question1"), rs.getString("answer1"), rs.getString("question2"), rs.getString("answer2"), rs.getString("profile_picture"),
                    rs.getString("bio"), rs.getString("status"));

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            user = null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    /**
     * Function to check for the change in passed object and update to database.
     * @param updateUser User object
     * @param username user username
     * @return updated User object
     */
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

            for (String change : changes) {

                switch (change) {
                    case "username":
                        if (updateUser.getUsername() == null) {
                            st.setString(changes.indexOf(change) + 1, username.toLowerCase());
                        } else {
                            st.setString(changes.indexOf(change) + 1, updateUser.getUsername().toLowerCase());
                        }
                        break;
                    case "first_name":
                        st.setString(changes.indexOf(change) + 1, updateUser.getFirst_name());
                        break;
                    case "last_name":
                        st.setString(changes.indexOf(change) + 1, updateUser.getLast_name());
                        break;
                    case "question1":
                        st.setString(changes.indexOf(change) + 1, updateUser.getQuestion1());
                        break;
                    case "question2":
                        st.setString(changes.indexOf(change) + 1, updateUser.getQuestion2());
                        break;
                    case "answer1":
                        st.setString(changes.indexOf(change) + 1, updateUser.getAnswer1());
                        break;
                    case "answer2":
                        st.setString(changes.indexOf(change) + 1, updateUser.getAnswer2());
                        break;
                    case "email":
                        st.setString(changes.indexOf(change) + 1, updateUser.getEmail());
                        break;
                    case "bio":
                        st.setString(changes.indexOf(change) + 1, updateUser.getBio());
                        break;
                    case "date_of_birth":
                        st.setString(changes.indexOf(change) + 1, updateUser.getDate_of_birth());
                        break;
                    case "profile_picture":
                        st.setString(changes.indexOf(change) + 1, updateUser.getProfile_picture());
                        break;
                    case "phone_number":
                        st.setString(changes.indexOf(change) + 1, updateUser.getPhone_number());
                        break;
                    case "password":
                        st.setString(changes.indexOf(change) + 1, updateUser.getPassword());
                        break;
                    case "gender":
                        st.setString(changes.indexOf(change) + 1, updateUser.getGender());
                        break;
                    case "role":
                        st.setString(changes.indexOf(change) + 1, updateUser.getRole());
                        break;
                    case "status":
                        st.setString(changes.indexOf(change) + 1, updateUser.getStatus());
                        break;
                    case "current_username":
                        st.setString(changes.indexOf(change) + 1, username);
                        break;
                }
            }

            st.executeUpdate();
            if (updateUser.getUsername() == null) {
                updateUser = getUser(username);
            } else {
                updateUser = getUser(updateUser.getUsername());
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            updateUser = null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return updateUser;
    }

    /**
     * Function to delete User.
     * @param username user username
     * @return true if success and false if failed
     */
    public boolean deleteUser(String username) {
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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Function to check in passed username is available or not.
     * @param username user username
     * @return true if available and false if not available
     */
    public boolean isUsernameAvailable(String username) {
        Connection con = null;
        boolean value = false;
        try {
            con = Database.getDatabase().getConnection();
            PreparedStatement st = con.prepareStatement("select * from user where username = ? ;");
            st.setString(1, username.toLowerCase());
            ResultSet rs = st.executeQuery();
            rs.last();
            value = rs.getRow() == 0 ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            value = false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    /**
     * Function to create a user in database.
     * @param user User Object
     * @return true success and false in failed
     */
    public boolean createUser(User user) {
        Connection con = null;
        try {
            con = Database.getDatabase().getConnection();
            String sql = "INSERT INTO user(username, password, first_name, last_name, role, "
                    + "email, phone_number, date_of_birth, gender, question1, answer1, "
                    + "question2, answer2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getFirst_name());
            st.setString(4, user.getLast_name());
            st.setString(5, user.getRole());
            st.setString(6, user.getEmail());
            st.setString(7, user.getPhone_number());
            st.setString(8, user.getDate_of_birth());
            st.setString(9, user.getGender());
            st.setString(10, user.getQuestion1());
            st.setString(11, user.getAnswer1());
            st.setString(12, user.getQuestion2());
            st.setString(13, user.getAnswer2());
            st.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    /**
     * Function to get total possible page Number of users with selected role.
     * @param role user role
     * @return total page number
     */
    public int getPagesNo(String role){
        Connection con = null;
        String sql;
        int recordsPerPage = 15;
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from user where role = ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, role);
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
    
    /**
     * Function to fetch users based on role.
     * @param page page no
     * @param role user role
     * @param sort sort type (asc / desc )
     * @return list of user
     */
    public List fetchUser(int page,String role, String sort){
        String sql;
        Connection con=null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page-1);
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from user where role = ? order by first_name "+sort+" limit ?,?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, role);
            st.setInt(2, skip);
            st.setInt(3, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToUser(rs);
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
    
    /**
     * 
     * @param search is a search keyword.
     * @param role user role
     * @param page starts from 1.
     * @return List searchedUsers
     */
    public List searchUser(String search, String role, int page){
        String sql;
        Connection con=null;
        PreparedStatement st;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page-1);
        
        try {
            con = Database.getDatabase().getConnection();
            if(role == null){
                sql = "select * from user where username like ? or first_name like ?"
                        + " or last_name like ? or phone_number like ? limit ?,? ;";
                st = con.prepareStatement(sql);
                st.setString(1, search+"%");
                st.setString(2, search+"%");
                st.setString(3, search+"%");
                st.setString(4, search);
                st.setInt(5, skip);
                st.setInt(6, limit);
            }
            else{
                sql = "select * from user where ( username like ? or first_name like ?"
                        + " or last_name like ? or phone_number like ? ) and role = ? limit ?,? ;";
                st = con.prepareStatement(sql);
                st.setString(1, search+"%");
                st.setString(2, search+"%");
                st.setString(3, search+"%");
                st.setString(4, search);
                st.setString(5, role);
                st.setInt(6, skip);
                st.setInt(7, limit);
            }
            
            ResultSet rs = st.executeQuery();
            return convertRSToUser(rs);
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
    
    /**
     * Function to get total possible page of a search query.
     * @param search search query
     * @param role role of user
     * @return total page number
     */
    public int totalSearchPages(String search,String role){
        String sql;
        Connection con=null;
        PreparedStatement st;
        int recordsPerPage = 15;
        
        try {
            con = Database.getDatabase().getConnection();
            if(role == null){
                sql = "select count(*) as count from user where username like ? or first_name like ?"
                        + " or last_name like ? or phone_number like ?;";
                st = con.prepareStatement(sql);
                st.setString(1, search+"%");
                st.setString(2, search+"%");
                st.setString(3, search+"%");
                st.setString(4, search);
            }
            else{
                sql = "select count(*) as count from user where username like ? or first_name like ?"
                        + " or last_name like ? or phone_number like ? and role = ?;";
                st = con.prepareStatement(sql);
                st.setString(1, search+"%");
                st.setString(2, search+"%");
                st.setString(3, search+"%");
                st.setString(4, search);
                st.setString(5, role);
            }
            
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
    
    /**
     * Function to get the total count of the passed role.
     * @param role - user role (admin/client)
     * @return - total count
     */
    public int totalUserCount(String role){
        Connection con = null;
        String sql;
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from user where role = ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, role);
            ResultSet rs = st.executeQuery();
            rs.next();
            int userCount = rs.getInt("count");
            return userCount;
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
    
    /**
     * Function to convert Result Set into list of User
     * @param rs Result Set object
     * @return list of User
     * @throws SQLException 
     */
    private List convertRSToUser(ResultSet rs) throws SQLException{
        List users = new ArrayList();
        while(rs.next()){
            users.add(new User(rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("role"), rs.getString("email"), rs.getString("phone_number"), rs.getString("date_of_birth"), rs.getString("gender"),
                     rs.getString("question1"), rs.getString("answer1"), rs.getString("question2"), rs.getString("answer2"), rs.getString("profile_picture"),
                    rs.getString("bio"), rs.getString("status")));
        }
        return users;
    }
    
}
