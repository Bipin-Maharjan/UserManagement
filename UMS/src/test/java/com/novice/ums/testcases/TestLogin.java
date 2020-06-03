/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.testcases;

import com.novice.ums.account.AccountFunctions;
import com.novice.ums.model.User;
import com.novice.ums.model.UserDAO;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author HP
 */
public class TestLogin {
    AccountFunctions functions;
    
    public TestLogin() {
        functions = new AccountFunctions();
        setUser();
    }
    
    private void setUser(){
        //clean up if same username
        if(!new UserDAO().isUsernameAvailable("test.test")){
            new UserDAO().deleteUser("test.test");
        }
        // add user to test
        User registerUser = new User("test.test", "test12345678", "test", "test", "admin",
                "test@test.com", "1234567890", "2020-05-11", "Male",
                "What is the first name of your best friend in high school?", "t1",
                "What is your dream job?", "t2", null, null, "active");
        functions.verrifyRegister(registerUser);
    }
    
    private void blockUser(){
        User user = new User();
        user.setStatus("blocked");
        new UserDAO().updateUser(user, "test.test");
    }
    
    @Test
    public void blankLogin(){
        String result = functions.verrifyLogin("", "");
        Assert.assertEquals("User is not registerd".toLowerCase(), result.toLowerCase());
    }
    
    @Test
    public void incorrectPasswordLogin(){
        String result = functions.verrifyLogin("test.test", "");
        Assert.assertEquals("Incorrect Username or Password".toLowerCase(), result.toLowerCase());
    }
    
    @Test
    public void incorrectUsernameLogin(){
        String result = functions.verrifyLogin("t3st.test", "test12345678");
        Assert.assertEquals("User is not registerd".toLowerCase(), result.toLowerCase());
    }
    
    @Test
    public void sucessfulLogin(){
        String result = functions.verrifyLogin("test.test", "test12345678");
        Assert.assertEquals("success".toLowerCase(), result.toLowerCase());
    }
    
    @Test
    public void blockedUserLogin(){
        blockUser();
        String result = functions.verrifyLogin("test.test", "test12345678");
        Assert.assertEquals("User is blocked. Please contact the Admin.".toLowerCase(), result.toLowerCase());
    }
}
