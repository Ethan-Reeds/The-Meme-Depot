/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.CookieHandler;
import java.net.CookieManager;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author techn
 */
public class PrivacyTest {

    static CookieManager cookieManager = new CookieManager();

    @BeforeClass
    public static void setupcookie() {
        CookieHandler.setDefault(cookieManager);

    }
    @BeforeMethod
    public void clearCookie() {
        cookieManager.getCookieStore().removeAll();
    }
    @BeforeClass
    public static void setup() {
        Main.startOrStopJetty(true);
    }
    @AfterClass
    public static void teardown() {
        Main.startOrStopJetty(false);
    }
    
    @BeforeMethod
    public void Clear(){
        TestUtility.fetch("/srv/clear");
    }
    
    @Test
    public void TestNotLoggedIn() {
        String response = TestUtility.post("/srv/privacy", "username=Spooky", "setting=Public");
        assertTrue(response.contains("Not logged in."));
    }
    
    //Checks for if trying to set the privacy setting of an account you aren't logged into
    @Test
    public void TestInvalidUsername() {
        TestUtility.post("/srv/register", "username=Spookyturbo", "password=12346", "email=Cat@turtle2.com", 
                         "day=27", "year=2000", "month=October");
        String response = TestUtility.post("/srv/privacy", "username=NotSpookyturbo", "setting=Public");
        assertTrue(response.contains("Not logged in."));
    }    
    
    @Test
    public void TestInvalidSetting() {
        TestUtility.post("/srv/register", "username=Spookyturbo", "password=12346", "email=Cat@turtle2.com", 
                         "day=27", "year=2000", "month=October");
        String response = TestUtility.post("/srv/privacy", "username=Spookyturbo", "setting=badvalue");
        assertTrue(response.contains("That privacy value does not exist"));
    }
    
    @Test
    public void TestValidSettings() {
        TestUtility.post("/srv/register", "username=pelican", "password=123", "email=t@turtle2.com", 
                         "day=27", "year=2000", "month=October");
        String response = TestUtility.post("/srv/privacy", "username=pelican", "setting=Public");
        assertTrue(response.contains("Success"));
        response = TestUtility.post("/srv/privacy", "username=pelican", "setting=Protected");
        assertTrue(response.contains("Success"));
        response = TestUtility.post("/srv/privacy", "username=pelican", "setting=Private");
        assertTrue(response.contains("Success"));
    }
    
    @Test
    public void TestProtectedPrivacy() {
        TestUtility.post("/srv/register", "username=pelican2", "password=12awd3", "email=tdw@turtle2.com", 
                         "day=27", "year=2000", "month=October");
        TestUtility.post("/srv/privacy", "username=pelican2", "setting=Protected");
        TestUtility.fetch("/srv/logout?username=pelican2");
        
        String response = TestUtility.fetch("/srv/user/pelican2");
        System.out.println(response);
        assertTrue(response.contains("You must be logged in to view this account."));
        
        //Login in to another account
        TestUtility.post("/srv/register", "username=pelican3", "password=12aawdwd3", "email=tdwdw@turtle2.com", 
                         "day=27", "year=2000", "month=October");
        
        response = TestUtility.fetch("/srv/user/pelican2");
        assertFalse(response.contains("You must be logged in to view this account."));
    }
}
