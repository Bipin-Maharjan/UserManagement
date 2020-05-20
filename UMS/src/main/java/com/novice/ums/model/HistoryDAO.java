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
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP All the database manipulation are written in DAO Class
 */
public class HistoryDAO {

    public void keepLog(String username, String type, String remark, String ip_address) {
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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getAllNoOfPages() {
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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getAllNoOfPages(String type) {
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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getNoOfPages(String username) {
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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getNoOfPages(String username, String type) {
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
        } finally {
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
            st.setString(1, search + "%");
            st.setString(2, type);
            ResultSet rs = st.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt("count");
            return (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
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
            st.setString(1, search + "%");
            ResultSet rs = st.executeQuery();
            rs.next();
            int noOfRecords = rs.getInt("count");
            return (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List getAllHistroy(int page) {
        String sql;
        Connection con = null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page - 1);

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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List getAllHistroy(String type, int page) {
        String sql;
        Connection con = null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page - 1);

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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List getHistroy(String username, int page) {
        String sql;
        Connection con = null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page - 1);

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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List getHistroy(String username, String type, int page) {
        String sql;
        Connection con = null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page - 1);

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
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List searchHistory(String searchquery, int page) {
        String sql;
        Connection con = null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page - 1);

        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where username like ? order by date_time desc limit ?,?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, searchquery + "%");
            st.setInt(2, skip);
            st.setInt(3, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List searchHistory(String searchquery, String type, int page) {
        String sql;
        Connection con = null;
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page - 1);

        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where username like ? and type = ? order by date_time desc limit ?,?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, searchquery + "%");
            st.setString(2, type);
            st.setInt(3, skip);
            st.setInt(4, limit);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List getColumnTypesFromRS() {
        String sql;
        Connection con = null;

        try {
            con = Database.getDatabase().getConnection();
            sql = "Select distinct(type) from history;";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List types = new ArrayList();
            while (rs.next()) {
                types.add(rs.getString("type"));
            }
            return types;
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List getColumnTypesFromRS(String username) {
        String sql;
        Connection con = null;

        try {
            con = Database.getDatabase().getConnection();
            sql = "Select distinct(type) from history where username = ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            List types = new ArrayList();
            while (rs.next()) {
                types.add(rs.getString("type"));
            }
            return types;
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List getSearchColumnTypesFromRS(String search) {
        String sql;
        Connection con = null;

        try {
            con = Database.getDatabase().getConnection();
            sql = "Select distinct(type) from history where username like ? ;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, search + "%");
            ResultSet rs = st.executeQuery();
            List types = new ArrayList();
            while (rs.next()) {
                types.add(rs.getString("type"));
            }
            return types;
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
    * This function will first query all the distinct username of not logout type records(i.e except logout) of previous 1 hrs
    (1hrs can be changes to 5 min for more accurate result. Here 1 hrs means if someone forgot to logout and close browser 
    then he/she will be shown online for 1 hrs more.)and check all the distinct username for their validity of online.
    * for checking validity of online. the code will query, the latest not logout record and the latest logout record 
    and compare its auto-increment id. If not logout id is greater than logout id then user is logged in.
    * Here Linked hash map is used instead of Hash Table because linked hash map preserve the insertion order where as hash table does not.
    **/
    public LinkedHashMap<String,String> getOnlineUser(int page,String sort) {
        String sql,starttime,endtime;
        Connection con = null;
        List<String> usernames = new ArrayList();
        LinkedHashMap<String,String> onlineDetails = new LinkedHashMap();
        PreparedStatement st;
        ResultSet rs;
        
        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page - 1);
        
        Calendar c = Calendar.getInstance();
        //year-month-day Hour-munute-second
        //remove -1 from hour and add -5 in minute for more accuracy.
        starttime = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH)+" "+(c.get(Calendar.HOUR_OF_DAY)-1)+":"+c.get(Calendar.MINUTE)+":00";
        endtime = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":00";
        
        try {
            con = Database.getDatabase().getConnection();
            sql = "select distinct(username) from history where date_time between ? and ? and type != 'logout' order by username "+sort+" limit ?,?;";
            st = con.prepareStatement(sql);
            st.setString(1, starttime);
            st.setString(2, endtime);
            st.setInt(3, skip);
            st.setInt(4, limit);
            rs = st.executeQuery();
            while(rs.next()){
                usernames.add(rs.getString("username"));
            }
            int login,logout;       
            for(String username : usernames){              
                sql = "Select history_id from history where username = ? and type = 'logout' order by date_time desc limit 1;";
                st = con.prepareStatement(sql);
                st.setString(1, username);
                rs = st.executeQuery();
                rs.last();
                logout = rs.getInt("history_id");
                
                sql = "Select history_id,date_time from history where username = ? and type != 'logout' order by date_time desc limit 1;";
                st = con.prepareStatement(sql);
                st.setString(1, username);
                rs = st.executeQuery();
                rs.last();
                login = rs.getInt("history_id");
                
                if(login > logout){
                    //online
                    onlineDetails.put(username, rs.getString("date_time"));
                }
            }     
            return onlineDetails;
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public List<History> getDateRangeData(String startDate, String endDate, String type){
        String sql;
        Connection con = null;

        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where date_time between ? and ? and type = ?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, startDate);
            st.setString(2, endDate);
            st.setString(3, type);
            ResultSet rs = st.executeQuery();
            return convertRSToHistory(rs);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private List convertRSToHistory(ResultSet rs) throws SQLException {
        List histories = new ArrayList();
        while (rs.next()) {
            histories.add(new History(rs.getString("username"), rs.getString("date_time"), rs.getString("type"), rs.getString("remark"), rs.getString("ip_address")));
        }
        return histories;
    }

}
