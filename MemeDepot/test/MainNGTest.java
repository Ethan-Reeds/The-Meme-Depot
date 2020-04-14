/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.CookieHandler;
import java.net.CookieManager;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
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
        TestUtility.fetch("/srv/clear");
    }
    
    // ############ REGISTER ################### 
    // standard login all entries provided
    @Test
    public void testRegister(){
        var response = TestUtility.post("/srv/register", "username=JFK@mafia.com", "password=Marilyn");
        assertTrue(response.contains("True"));        
    }
    
    // trying to register withoug using any arguments
    @Test
    public void testRegisterNoArgs(){
        var response = TestUtility.post("/srv/register");
        assertTrue(response.contains("False"));
    }
    
    // trying to register without a username
    @Test
    public void testRegisterNoUsername() {
        var response = TestUtility.post("/srv/register", "password=Marilyn");
        assertTrue(response.contains("False"));
    }
    
    // trying to register without a password
    @Test
    public void testRegisterNoPassword() {
        var response = TestUtility.post("/srv/register", "username=JFK@mafia.com");
        assertTrue(response.contains("False"));
    }
    
    // trying to register an existing account
    @Test
    public void testRegisterExisting(){
        TestUtility.post("/srv/register", "username=JFK@mafia.com", "password=Marilyn");
        var response = TestUtility.post("/srv/register", "username=JFK@mafia.com", "password=Marilyn");
        assertTrue(response.contains("duplicate username"));
    }
    
    // trying to register with an email that is not "syntacticaly" an email
    @Test
    public void testRegisterBadEmail(){
        var response = TestUtility.post("/srv/register", "username=JFK", "password=Marilyn", "email=Cow");
        assertTrue(response.contains("False"));
        response = TestUtility.post("/srv/register", "username=JFK", "password=Marilyn", "email=Cow@");
        assertTrue(response.contains("False"));
        response = TestUtility.post("/srv/register", "username=JFK", "password=Marilyn", "email=@");
        assertTrue(response.contains("False"));
        response = TestUtility.post("/srv/register", "username=JFK", "password=Marilyn", "email=@Cow");
        assertTrue(response.contains("False"));
    }
    
    // ############# LOGIN ###############
    // standard login all entries provided
    @Test 
    public void Login() {   
        TestUtility.post("/srv/register", "username=JFK@mafia.com", "password=Marilyn");
        var response = TestUtility.fetch("/srv/login?username=JFK@mafia.com&password=Marilyn");
        assertTrue(response.contains("True"));
    }
    
    // login but with no arguments
    @Test 
    public void LoginNoArgs() {
        TestUtility.post("/srv/register", "username=JFK@mafia.com", "password=Marilyn");
        var response = TestUtility.fetch("/srv/login?");
        assertTrue(response.contains("Null"));
    }
    
    // login but using the wrong password
    @Test 
    public void LoginWrongPassword() {
        TestUtility.post("/srv/register", "username=JFK@mafia.com", "password=Marilyn");
        var response = TestUtility.fetch("/srv/login?username=JFK@mafia.com&password=wrongPassword");
        assertTrue(response.contains("False"));
    }
    
    // trying to log into a seperate account or loggin into a different account without logging out
    @Test
    public void LoginAnotherAccount(){
        TestUtility.post("/srv/register", "username=JFK@mafia.com", "password=Marilyn");
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        TestUtility.fetch("/srv/login?username=JFK@mafia.com&password=Marilyn");
        var response = TestUtility.fetch("/srv/login?username=JFK@mafia.com&password=Marilyn");
        assertTrue(response.contains("False"));
    }
    
    // ##### WHO / HOME PAGE #######
    // tests who page when you are logged in
    @Test
    public void who(){  
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        TestUtility.fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = TestUtility.fetch("/srv/who");   // who uses the session to see if youre logged in
        assertTrue(response.contains("True"));
        
    }
    
    // tests who page when you are not logged in
    @Test
    public void whoNotLoggedIn(){  
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        var response = TestUtility.fetch("/srv/who");   // who uses the session to see if youre logged in
        assertTrue(response.contains("False"));
        
    }
    
    // ##### Logout #######
    // tests when correct conditions have been met
    @Test
    public void Logout(){
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        TestUtility.fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = TestUtility.fetch("/srv/logout?username=JimmyHoffa@mafia.com");
        assertTrue(response.contains("True"));    
    }
    
    // tests when you are not logged in
    @Test
    public void LogoutNotLoggedIn(){
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        var response = TestUtility.fetch("/srv/logout?username=JimmyHoffa@mafia.com");
        assertTrue(response.contains("False"));    
    }
    
    // tests when no parameters are given and user is logged in
    @Test
    public void LogoutNoUsrNameLoggedIn(){
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        //TestUtility.fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = TestUtility.fetch("/srv/logout");
        assertTrue(response.contains("False"));
    }
    
    // tests when no parameters are given and no one is logged in
    @Test
    public void LogoutNoUsrNameNotLoggedIn(){
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        TestUtility.fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = TestUtility.fetch("/srv/logout");
        assertTrue(response.contains("False"));
    }
    // tests search when no one is logged in
    @Test
    public void SearchForUserNoUserLoggedIn(){
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        var response = TestUtility.fetch("/srv/search?search=JimmyHoffa@mafia.com");
        assertTrue(response.contains("JimmyHoffa@mafia.com"));
        
    }
    // tests search when search input is single char
    @Test
    public void SearchForUserWithSingleChar(){
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        //TestUtility.fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = TestUtility.fetch("/srv/search?search=J");
        assertTrue(response.contains("JimmyHoffa@mafia.com"));
        
    }
     // tests search when search input is single char
    @Test
    public void SearchForUserLoggedIn(){
        TestUtility.post("/srv/register", "username=JimmyHoffa@mafia.com", "password=IDEAD");
        TestUtility.fetch("/srv/login?username=JimmyHoffa@mafia.com&password=IDEAD");
        var response = TestUtility.fetch("/srv/search?search=JimmyHoffa@mafia.com");
        assertTrue(response.contains("No Matchs"));
        
    }
}
