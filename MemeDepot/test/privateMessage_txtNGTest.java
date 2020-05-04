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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author user
 */
public class privateMessage_txtNGTest {
    
    public privateMessage_txtNGTest() {
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
    
    @BeforeMethod
    public void make_accts(){
        messageManager mInstance = new messageManager();
        Account acct1 = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20");
        Account acct2 = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35");
        AccountManager.addUser(acct1.username,acct1.password,acct1.email,"220","04","20");
        AccountManager.addUser(acct2.username,acct2.password,acct2.email,"2343","02","35");
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

    /**
     * Test of doPost method, of class privateMessage_txt.
     */
    @Test
    public void everything_correct() throws Exception {
        System.out.println("everything_correct");
        // servlet handles creating the message and getting the accounts
        fetch("/srv/login?username=alecBaldwin@imGreat.com&password=30Rock!");
        // logged in so that the session can check for the username to find the correct account
        var response = fetch("/srv/pm_txt?to=markyMark@funkyBunch.com&message=hey+do+you+want+to+be+on+30+rock?+lol");
        assert(response.contains("hey+do+you+want+to+be+on+30+rock?+lol"));
    }
    @Test
    public void recipient_doesnt_exist() throws Exception {
        System.out.println("recipient_doesnt_exist");
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        pm_txt instance = new pm_txt();
        //instance.doPost(req, resp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void incorrect_parameters() throws Exception {
        System.out.println("incorrect_parameters");
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        pm_txt instance = new pm_txt();
        //instance.doPost(req, resp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
