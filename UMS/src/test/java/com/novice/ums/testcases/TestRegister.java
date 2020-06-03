/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novice.ums.testcases;

import com.novice.ums.account.AccountFunctions;
import com.novice.ums.model.User;
import com.novice.ums.model.UserDAO;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author HP
 */
public class TestRegister {
    AccountFunctions functions;
    
    public TestRegister() {
        functions =  new AccountFunctions();
        
        //clean up befre starting
        if(!new UserDAO().isUsernameAvailable("test.test")){
            new UserDAO().deleteUser("test.test");
        }
    }
    
    @Test
    public void blankRegister() {
        User registerUser = new User("","","","","","","","","","","","","",null,null,"");
        Object result = functions.verrifyRegister(registerUser);
        Assert.assertEquals(ArrayList.class,result.getClass());
    }
    
    @Test
    public void oneFieldEmptyRegister() {
        User registerUser = new User("test.test", "test12345678", "test", "test", "admin",
                "test@test.com", "1234567890", "2020-05-11", "Male",
                "What is the first name of your best friend in high school?", "t1",
                "What is your dream job?", "t2", null, null, "");
        Object result = functions.verrifyRegister(registerUser);
        Assert.assertEquals(ArrayList.class,result.getClass());
    }
    
    @Test
    public void sucessfulRegister() {
        User registerUser = new User("test.test", "test12345678", "test", "test", "admin",
                "test@test.com", "1234567890", "2020-05-11", "Male",
                "What is the first name of your best friend in high school?", "t1",
                "What is your dream job?", "t2", null, null, "active");
        Object result = functions.verrifyRegister(registerUser);
        Assert.assertEquals("".getClass(),result.getClass());
    }
    
    @Test
    public void incorrectPhonenumberRegister() {
        User registerUser = new User("test.test", "test12345678", "test", "test", "admin",
                "test@test.com", "1234567", "2020-05-11", "Male",
                "What is the first name of your best friend in high school?", "t1",
                "What is your dream job?", "t2", null, null, "active");
        Object result = functions.verrifyRegister(registerUser);
        Assert.assertEquals(ArrayList.class,result.getClass());
    }
    
    @Test
    public void oneFieldFilledRegister() {
        User registerUser = new User("","","test","","","","","","","","","","",null,null,"");
        Object result = functions.verrifyRegister(registerUser);
        Assert.assertEquals(ArrayList.class,result.getClass());
    }
}
