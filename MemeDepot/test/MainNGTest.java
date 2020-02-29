/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Ethan Reeds
 */
public class MainNGTest {
    
    public MainNGTest() {
    }

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
        fetch("/srv/clear");
    }
        
    static String fetch(String... allurls) {
        try {
            String str = null;
            byte[] returnedData = new byte[]{0};  //dummy
            for (String oneurl : allurls) {
                var url = new URL("http://localhost:2020" + oneurl);
                var conn = url.openConnection();
                conn.connect();
                var istr = conn.getInputStream();
                returnedData = istr.readAllBytes();
            }
            return new String(returnedData, 0, returnedData.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    // ############ REGISTER ################### 
    // standard login all entries provided
    @Test
    public void testRegister(){
        var response = fetch("/srv/register?username=JFK@mafia.com&password=Marilyn");
        assert(response.contains("True"));
    }
    
    // trying to register withoug using any arguments
    @Test
    public void testRegisterNoArgs(){
        var response = fetch("/srv/register?");
        assert(response.contains("Null"));
    }
    
    // trying to register an existing account
    @Test
    public void testRegisterExisting(){
        fetch("/srv/register?username=JFK@mafia.com&password=Marilyn");
        var response = fetch("/srv/register?username=JFK@mafia.com&password=Marilyn");
        assert(response.contains("False"));
    }
    
    // trying to register with an email that is not "syntacticaly" an email
    @Test
    public void testRegisterBadEmail(){
        var response = fetch("/srv/register?username=JFK&password=Marilyn");
        assert(response.contains("False"));
    }
    
    // ############# LOGIN ###############
    // standard login all entries provided
    @Test 
    public void Login() {   
        fetch("/srv/register?username=JFK@mafia.com&password=Marilyn");
        var response = fetch("/srv/login?username=JFK@mafia.com&password=Marilyn");
        assert(response.contains("True"));
    }
    
    // login but with no arguments
    @Test 
    public void LoginNoArgs() {
        fetch("/srv/register?username=JFK@mafia.com&password=Marilyn");
        var response = fetch("/srv/login?");
        assert(response.contains("Null"));
    }
    
    // login but using the wrong password
    @Test 
    public void LoginWrongPassword() {
        fetch("/srv/register?username=JFK@mafia.com&password=Marilyn");
        var response = fetch("/srv/login?username=JFK@mafia.com&password=wrongPassword");
        assert(response.contains("False"));
    }
    
    // trying to log into a seperate account or loggin into a different account without logging out
    @Test
    public void LoginAnotherAccount(){
        fetch("/srv/register?username=JFK@mafia.com&password=Marilyn");
        fetch("/srv/register?username=JimmyHoffa@mafia.com&password=IDEAD");
        fetch("/srv/login?username=JFK@mafia.com&password=Marilyn");
        var response = fetch("/srv/login?username=JFK@mafia.com&password=Marilyn");
        assert(response.contains("False"));
    }
    
    // ##### WHO / HOME PAGE #######
    // tests who page when you are logged in
    @Test
    public void who(){  
        fetch("/srv/register?username=JimmyHoffa@mafia.com&password=IDEAD");
        fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = fetch("/srv/who");   // who uses the session to see if youre logged in
        assert(response.contains("True"));
        
    }
    
    // tests who page when you are not logged in
    @Test
    public void whoNotLoggedIn(){  
        fetch("/srv/register?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = fetch("/srv/who");   // who uses the session to see if youre logged in
        assert(response.contains("False"));
        
    }
    
    // ##### Logout #######
    // tests when correct conditions have been met
    @Test
    public void Logout(){
        fetch("/srv/register?username=JimmyHoffa@mafia.com&password=IDEAD");
        fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = fetch("/srv/logout?username=JimmyHoffa@mafia.com");
        assert(response.contains("True"));    
    }
    
    // tests when you are not logged in
    @Test
    public void LogoutNotLoggedIn(){
        fetch("/srv/register?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = fetch("/srv/logout?username=JimmyHoffa@mafia.com");
        assert(response.contains("False"));    
    }
    
    // tests when no parameters are given and user is logged in
    @Test
    public void LogoutNoUsrNameLoggedIn(){
        fetch("/srv/register?username=JimmyHoffa@mafia.com&password=IDEAD");
        //fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = fetch("/srv/logout");
        assert(response.contains("False"));
    }
    
    // tests when no parameters are given and no one is logged in
    @Test
    public void LogoutNoUsrNameNotLoggedIn(){
        fetch("/srv/register?username=JimmyHoffa@mafia.com&password=IDEAD");
        fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = fetch("/srv/logout");
        assert(response.contains("False"));
    }
}
