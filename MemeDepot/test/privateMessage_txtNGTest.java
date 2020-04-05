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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.util.StringContentProvider;
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
    static String post(String location, String ...keyVal){
        try {
            HttpClient jettyClient = new HttpClient();
            var prov = new org.eclipse.jetty.client.util.MultiPartContentProvider();
            for (var i : keyVal){
                String[] tmp = i.split("=");
                prov.addFieldPart(tmp[0],
                    new StringContentProvider(tmp[1]),
                    null);
            }
            prov.close();
            
            jettyClient.start();
            
            var req = jettyClient.newRequest("http://localhost:2020/srv/" + location);
            req.method("POST");
            req.content(prov);
            var resp = req.send();
            
            jettyClient.stop();
            
            return resp.getContentAsString();
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
        
        messageManager mInstance = new messageManager();
        Account acct1 = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20","12323234");
        Account acct2 = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35","394858783");
        post("register", "username=alecBaldwin@imGreat.com", "password=30Rock!");
        post("register", "username=markyMark@funkyBunch.com", "password=imAnActorN0w");
        var response = post("pm_txt","to=markyMark@funkyBunch.com", "from=alecBaldwin@imGreat.com","message=want to be on 30 rock?");
        assert(response.contains("want to be on 30 rock?"));
    }
    
    @Test
    public void everything_correct_mulitpleMessages() throws Exception {
        System.out.println("everything_correct (more than one message)");
        
        messageManager mInstance = new messageManager();
        Account acct1 = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20","12323234");
        Account acct2 = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35","394858783");
        post("register", "username=alecBaldwin@imGreat.com", "password=30Rock!");
        post("register", "username=markyMark@funkyBunch.com", "password=imAnActorN0w");
        post("pm_txt","to=markyMark@funkyBunch.com", "from=alecBaldwin@imGreat.com","message=want to be on 30 rock?");
        var response = post("pm_txt","to=alecBaldwin@imGreat.com", "from=markyMark@funkyBunch.com","message=nah");
        System.out.println(response);
        assert(response.contains("want to be on 30 rock?\n"
                + "nah"));
    }
    
    @Test
    public void recipient_doesnt_exist() throws Exception {
        System.out.println("recipient does not exist");
        
        messageManager mInstance = new messageManager();
        Account acct1 = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20","12323234");
        post("register", "username=alecBaldwin@imGreat.com", "password=30Rock!");
        var response = post("pm_txt","to=markyMark@funkyBunch.com", "from=alecBaldwin@imGreat.com","message=want to be on 30 rock?");
        assert(response.contains("Account(s) do not exist"));
    }
    @Test
    public void incorrect_parameters_0() throws Exception {
        System.out.println("incorrect_parameters (missing message)");
        
        messageManager mInstance = new messageManager();
        Account acct1 = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20","12323234");
        Account acct2 = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35","394858783");
        post("register", "username=alecBaldwin@imGreat.com", "password=30Rock!");
        post("register", "username=markyMark@funkyBunch.com", "password=imAnActorN0w");
        var response = post("pm_txt","to=markyMark@funkyBunch.com", "from=alecBaldwin@imGreat.com");
        assert(response.contains("No message"));
    }
    
    @Test
    public void incorrect_parameters_1() throws Exception {
        System.out.println("incorrect_parameters (no sender)");
        
        messageManager mInstance = new messageManager();
        Account acct1 = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20","12323234");
        Account acct2 = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35","394858783");
        post("register", "username=alecBaldwin@imGreat.com", "password=30Rock!");
        post("register", "username=markyMark@funkyBunch.com", "password=imAnActorN0w");
        var response = post("pm_txt","to=markyMark@funkyBunch.com","message=want to be on 30 rock?");
        assert(response.contains("Username(s) missing"));
    }
    
}
