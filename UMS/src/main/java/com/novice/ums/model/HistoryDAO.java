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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP All the database manipulation are written in DAO Class
 */
public class HistoryDAO {

    /**
     * Function to insert log in to history table
     * @param username user username
     * @param type history type
     * @param remark remark
     * @param ip_address ip_address
     */
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

    /**
     * Function to get total possible page no of history data. One page Contains 15 records.
     * @return total page number
     */
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

    /**
     * Function to get total possible page number of certain history type.
     * @param type history type
     * @return total page number
     */
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

    /**
     * Function to get total possible page number of one users history.
     * @param username user username
     * @return total page number
     */
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

    /**
     * Function to get total possible page number of user history with type.
     * @param username user username
     * @param type history type
     * @return 
     */
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

    /**
     * Function to get total possible page number of a search query with a history type
     * @param search search query
     * @param type history type
     * @return total page number
     */
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

    /**
     * FUnction to get total possible page number of a search query.
     * @param search search query
     * @return total page number
     */
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

    /**
     * Function to get all history from history table
     * @param page page no
     * @return list of histories
     */
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

    /**
     * Function to get all history of certain history type.
     * @param type history type
     * @param page page no
     * @return list of History
     */
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

    /**
     * Function to get history of selected user.
     * @param username user username
     * @param page page no
     * @return list of history
     */
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

    /**
     * Function to get history of selected user with history type filter.
     * @param username user username
     * @param type history type
     * @param page page no
     * @return list of history
     */
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

    /**
     * Function to get history of searched query.
     * @param searchquery search query
     * @param page page no
     * @return list of history
     */
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

    /**
     * Function to get history of searched query with history filter
     * @param searchquery search query
     * @param type history type
     * @param page page no
     * @return list of history
     */
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

    /**
     * Function to get history column type.
     * @return list of columns
     */
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

    /**
     * Function to get history column type of certain user
     * @param username user username
     * @return lust of column
     */
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

    /**
     * Function to get column type of search keyword.
     * @param search search query
     * @return list of column
     */
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
     * This function will first query all the distinct username of not logout
     * type records(i.e except logout) of previous 1 hrs (1hrs can be changes to
     * 5 min for more accurate result.Here 1 hrs means if someone forgot to
     * logout and close browser then he/she will be shown online for 1 hrs
     * more.)and check all the distinct username for their validity of online.
     * for checking validity of online. The code will query the latest not
     * logout record and the latest logout record and compare its id. If not
     * logout records id is greater than logout records id then user is logged
     * in else logged out. Here Linked hash map is used instead of Hash Table
     * because linked hash map preserve the insertion order where as hash table
     * does not.
     *
     * @param page - Page Number like 1,2
     * @param sort - sorting order asc or desc
     * @return - LinkedHashMap containing username and date time.
     *
     */
    public LinkedHashMap<String, String> getOnlineUser(int page, String sort) {
        String sql, starttime, endtime;
        Connection con = null;
        List<String> usernames = new ArrayList();
        LinkedHashMap<String, String> onlineDetails = new LinkedHashMap();
        PreparedStatement st;
        ResultSet rs;

        int recordsPerPage = 15;
        int limit = recordsPerPage * page;
        int skip = recordsPerPage * (page - 1);

        Calendar c = Calendar.getInstance();
        //year-month-day Hour-munute-second
        //REPLACE `c.add(Calendar.HOUR_OF_DAY, -1);` with `c.add(Calendar.MINUTE, -5);` for more accuracy
        endtime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":00";
        c.add(Calendar.HOUR_OF_DAY, -1);
        starttime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + (c.get(Calendar.HOUR_OF_DAY)) + ":" + c.get(Calendar.MINUTE) + ":00";

        try {
            con = Database.getDatabase().getConnection();
            sql = "select distinct(username) from history where date_time between ? and ? and type != 'logout' order by username " + sort + " limit ?,?;";
            st = con.prepareStatement(sql);
            st.setString(1, starttime);
            st.setString(2, endtime);
            st.setInt(3, skip);
            st.setInt(4, limit);
            rs = st.executeQuery();
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
            int login, logout;
            for (String username : usernames) {
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

                if (login > logout) {
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

    /**
     * Function to get the online user count. Refer to getOnlineUser for more
     * detail explanation.
     *
     * @return total online count
     */
    public int getOnlineUserCount() {
        String sql, starttime, endtime;
        Connection con = null;
        List<String> usernames = new ArrayList();
        PreparedStatement st;
        ResultSet rs;

        Calendar c = Calendar.getInstance();
        //year-month-day Hour-munute-second
        //remove -1 from hour and add -5 in minute for more accuracy.
        endtime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":00";
        c.add(Calendar.HOUR_OF_DAY, -1);
        starttime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + (c.get(Calendar.HOUR_OF_DAY)) + ":" + c.get(Calendar.MINUTE) + ":00";

        try {
            con = Database.getDatabase().getConnection();
            sql = "select distinct(username) from history where date_time between ? and ? and type != 'logout';";
            st = con.prepareStatement(sql);
            st.setString(1, starttime);
            st.setString(2, endtime);
            rs = st.executeQuery();
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
            int login, logout, online = 0;
            for (String username : usernames) {
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

                if (login > logout) {
                    //online
                    online += 1;
                }
            }
            return online;
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

    /**
     * Function to history data of the given date range
     * @param startDate date in string '2020-12-26'
     * @param endDate date in string '2020-12-26'
     * @param type history type
     * @return list of history
     */
    public List<History> getDateRangeData(String startDate, String endDate, String type) {
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

    /**
     * Function to get Last visitors with timeSpent of last visitors. If user
     * login and do not perform any action in the site and do not logout then
     * their usage time will be 10 min.
     *
     * @param visitorsCount - count of last visitors you want
     * @return - list of histories. Time spent is attached to `extra info` and
     * Name is added to `extra_info2` of history object.
     */
    public List<History> getLastVisitors(int visitorsCount) {
        Connection con = null;
        String sql;
        List histories;
        PreparedStatement st;
        ResultSet rs, innerRs1, innerRs2;
        String username, timespent;
        int login, logout;
        try {
            con = Database.getDatabase().getConnection();
            sql = "select distinct(username) from history where type = 'login' order by date_time desc limit ?";
            st = con.prepareStatement(sql);
            st.setInt(1, visitorsCount);
            rs = st.executeQuery();
            histories = new ArrayList();
            while (rs.next()) {
                username = rs.getString("username");
                st = con.prepareStatement("select history.history_id, history.username, history.date_time, history.type, "
                        + "history.remark, history.ip_address, user.first_name, user.last_name from history join user "
                        + "on history.username = user.username where history.username = ? and history.type = 'login' "
                        + "order by history.date_time desc limit 1;");
                st.setString(1, username);
                innerRs1 = st.executeQuery();
                if(innerRs1.next()){
                    login = innerRs1.getInt("history_id");
                }
                else{
                    login = 0;
                }

                st = con.prepareStatement("select * from history where username = ? and type != 'login' order by date_time desc limit 1;");
                st.setString(1, username);
                innerRs2 = st.executeQuery();
                if(innerRs2.next()){
                    logout = innerRs2.getInt("history_id");
                }
                else{
                    logout = 0;
                }

                if (logout > login) {
                    //login and logout successfully
                    timespent = claculateTimeSpent(innerRs1.getString("date_time"), innerRs2.getString("date_time"));
                    History history = new History(innerRs1.getString("username"), innerRs1.getString("date_time"),
                            innerRs1.getString("type"), innerRs1.getString("remark"), innerRs1.getString("ip_address"));
                    history.setExtra_info(timespent);
                    history.setExtra_info2(capitalize(innerRs1.getString("first_name")) + " "
                            + capitalize(innerRs1.getString("last_name")));
                    histories.add(history);
                } else {
                    timespent = "10m 0s";
                    History history = new History(innerRs1.getString("username"), innerRs1.getString("date_time"),
                            innerRs1.getString("type"), innerRs1.getString("remark"), innerRs1.getString("ip_address"));
                    history.setExtra_info(timespent);
                    history.setExtra_info2(capitalize(innerRs1.getString("first_name")) + " "
                            + capitalize(innerRs1.getString("last_name")));
                    histories.add(history);
                }
            }

            Collections.sort(histories, new Comparator<History>() {
                @Override
                public int compare(History o1, History o2) {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(o1.getDate_time())
                                .compareTo(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(o2.getDate_time()));
                    } catch (ParseException ex) {
                        Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                        return 0;
                    }
                }
            });
            Collections.reverse(histories);
            return histories;
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
     * Function for getting the LastLogins of the particular user.
     *
     * @param loginCount - no of last logins you want
     * @param username - users username to get the last login history.
     * @return list of history containing last login details
     */
    public List<History> getLastLogins(int loginCount, String username) {
        Connection con = null;
        String sql;
        List<History> histories = new ArrayList();
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select * from history where username = ? and type = 'login' order by date_time desc limit ?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            st.setInt(2, loginCount);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                histories.add(new History(rs.getString("username"), rs.getString("date_time"),
                        rs.getString("type"), rs.getString("remark"), rs.getString("ip_address")));
            }
            Collections.sort(histories, new Comparator<History>() {
                @Override
                public int compare(History o1, History o2) {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(o1.getDate_time())
                                .compareTo(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(o2.getDate_time()));
                    } catch (ParseException ex) {
                        Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                        return 0;
                    }
                }
            });
            Collections.reverse(histories);
            return histories;
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
     * Function to get the last 7 days time spent of the passed user. 
     * if user forget to logout then default time will be 10 minute.
     * This do not show current days time usage
     *
     * @param username - username of the user
     * @return list of history object only containing the `extra info` and
     * `extra info 2`. `extra_info` contains time spent and `extra_info2`
     * contains day.
     */
    public List<History> getLast7DaysTimeSpent(String username) {
        String sql, startDate, endDate;
        int day;
        Connection con = null;
        List<History> histories = new ArrayList();
        PreparedStatement st;
        ResultSet rs1, rs2;
        Date date1, date2;

        Calendar c = Calendar.getInstance();

        try {
            con = Database.getDatabase().getConnection();
            for (int i = 0; i < 7; i++) {
                History history = new History();
                startDate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
                c.add(Calendar.DAY_OF_MONTH, -1);
                endDate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
                day = c.get(Calendar.DAY_OF_WEEK);
                sql = "select * from history where username = ? and date_time between ? and ? and type = 'login' order by date_time desc limit 1;";
                st = con.prepareStatement(sql);
                st.setString(1, username);
                st.setString(2, endDate);
                st.setString(3, startDate);
                rs1 = st.executeQuery();

                sql = "select * from history where username = ? and date_time between ? and ? and type != 'login' order by date_time desc limit 1;";
                st = con.prepareStatement(sql);
                st.setString(1, username);
                st.setString(2, endDate);
                st.setString(3, startDate);
                rs2 = st.executeQuery();
                if (rs1.next()) {
                    rs2.next();
                    //logout date - login date
                    if (rs2.getInt("history_id") > rs1.getInt("history_id")) {
                        //login and logout successfully
                        try {
                            //login
                            date1 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(rs1.getString("date_time"));
                            //logout
                            date2 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(rs2.getString("date_time"));

                            long diff = date2.getTime() - date1.getTime();
                            int seconds = (int) (diff / 1000) % 60;
                            int minutes = (int) ((diff / (1000 * 60)) % 60);
                            int hours = (int) ((diff / (1000 * 60 * 60)) % 24);
                            
                            float timespent = minutes + hours * 60+(float)Math.round((seconds/60.0)*10)/10;
                            history.setExtra_info(String.valueOf(timespent));
                            
                        } catch (ParseException ex) {
                            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        history.setExtra_info(String.valueOf(10));
                    }
                }
                else{
                    history.setExtra_info(String.valueOf(0));
                }
                switch (day) {
                    case 1:
                        history.setExtra_info2("Sunday");
                        break;
                    case 2:
                        history.setExtra_info2("Monday");
                        break;
                    case 3:
                        history.setExtra_info2("Tuesday");
                        break;
                    case 4:
                        history.setExtra_info2("Wednesday");
                        break;
                    case 5:
                        history.setExtra_info2("Thrusday");
                        break;
                    case 6:
                        history.setExtra_info2("Friday");
                        break;
                    case 7:
                        history.setExtra_info2("Saturday");
                        break;
                    default:
                        history.setExtra_info2("ANON");
                        break;
                }
                histories.add(history);
            }
            Collections.reverse(histories);
            return histories;
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
     * Function to get the top active user. This function loads and compare all
     * the users login count and predict the output.
     *
     * @return History object list with most active user at 1st.
     */
    public List<History> getMostActiveUser() {
        Connection con = null;
        String sql, username;
        PreparedStatement st;
        ResultSet rs, innerRs;
        List<History> sortedHistories = new ArrayList();
        try {
            con = Database.getDatabase().getConnection();
            sql = "select distinct(username) from history where type = 'login'";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                username = rs.getString("username");
                sql = "select history.history_id, history.username, history.date_time, history.type, "
                        + "history.remark, history.ip_address, user.first_name, user.last_name, count(*) as count from history join user "
                        + "on history.username = user.username where history.username = ? and history.type = 'login';";
                st = con.prepareStatement(sql);
                st.setString(1, username);
                innerRs = st.executeQuery();
                innerRs.next();
                History history = new History(innerRs.getString("username"), innerRs.getString("date_time"),
                        innerRs.getString("type"), innerRs.getString("remark"), innerRs.getString("ip_address"));
                history.setExtra_info(innerRs.getString("count"));
                history.setExtra_info2(capitalize(innerRs.getString("first_name")) + " "
                        + capitalize(innerRs.getString("last_name")));
                sortedHistories.add(history);
            }
            Collections.sort(sortedHistories, new Comparator<History>() {
                @Override
                public int compare(History o1, History o2) {
                    return Integer.parseInt(o1.getExtra_info()) - Integer.parseInt(o2.getExtra_info());
                }
            });
            Collections.reverse(sortedHistories);
            return sortedHistories;
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
     * Function to get new user count with in given days.
     *
     * @param timeInDays - days to limit
     * @return - total new user count
     */
    public int getNewUserCount(int timeInDays) {
        Connection con = null;
        String sql;
        Calendar c = Calendar.getInstance();
        String starttime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
        c.add(Calendar.DAY_OF_MONTH, -7);
        String endtime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + (c.get(Calendar.DAY_OF_MONTH));
        try {
            con = Database.getDatabase().getConnection();
            sql = "Select count(*) as count from history where type = 'User Register' or type = 'User added by Admin' and date_time between ? and ?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, endtime);
            st.setString(2, starttime);
            ResultSet rs = st.executeQuery();
            rs.next();
            int newUserCount = rs.getInt("count");
            return newUserCount;
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

    /**
     * Function for calculate time spent between two dates.
     *
     * @param loginDate
     * @param logoutDate
     * @return - difference between toe times.
     */
    private String claculateTimeSpent(String loginDate, String logoutDate) {
        Date date1, date2;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(loginDate);
            date2 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(logoutDate);

            long diff = date2.getTime() - date1.getTime();
            int seconds = (int) (diff / 1000) % 60;
            int minutes = (int) ((diff / (1000 * 60)) % 60);
            int hours = (int) ((diff / (1000 * 60 * 60)) % 24);

            if (hours == 0) {
                if (minutes == 0) {
                    return seconds + "s";
                } else {
                    return minutes + "m";
                }
            } else if (minutes == 0) {
                return hours + "h";
            } else {
                return hours + "h, " + minutes + "m";
            }
        } catch (ParseException ex) {
            Logger.getLogger(HistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Function to capitalize the word
     *
     * @param word
     * @return Capitalize word
     */
    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    /**
     * Function to convert history ResultSet into list of history
     * @param rs ResultSet object
     * @return list of history
     * @throws SQLException 
     */
    private List convertRSToHistory(ResultSet rs) throws SQLException {
        List histories = new ArrayList();
        while (rs.next()) {
            histories.add(new History(rs.getString("username"), rs.getString("date_time"), rs.getString("type"), rs.getString("remark"), rs.getString("ip_address")));
        }
        return histories;
    }

}
