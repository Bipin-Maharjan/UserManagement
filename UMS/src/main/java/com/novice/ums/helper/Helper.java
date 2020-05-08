/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.helper;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author HP
 */
public class Helper {
    public static String hashPassword(String text) {        
        String hashEncode = DigestUtils.sha256Hex(text);
        return hashEncode.toUpperCase();
    }
}
