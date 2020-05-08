/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.model;

/**
 *
 * @author HP
 * This is BEANS Class just to hold the data
 */
public class History {
    private int history_id;
    private String username;
    private String date_time;
    private String type;
    private String remark;
    private String ip_address;
    
    public History(){
        this.history_id = 0;
        this.date_time = null;
        this.username = null;
        this.type = null;
        this.remark = null;
        this.ip_address = null;
    }

    public History(String username, String type, String remark, String ip_address) {
        this.username = username;
        this.type = type;
        this.remark = remark;
        this.ip_address = ip_address;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }    
}
