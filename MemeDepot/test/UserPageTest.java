import java.net.CookieHandler;
import java.net.CookieManager;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Andy Riedlinger
 */
public class UserPageTest {
    
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
    public void noUserGiven() {
        String response = TestUtility.fetch("/srv/user");
        assertTrue(response.contains("No parameters given"));
        response = TestUtility.fetch("/srv/user/");
        assertTrue(response.contains("No parameters given"));
    }
    
    @Test
    public void tooManyParameters() {
        String response = TestUtility.fetch("/srv/user/Crab/Dog");
        assertTrue(response.contains("Too many parameters"));
        response = TestUtility.fetch("/srv/user/Crab/Dog/Cat");
        assertTrue(response.contains("Too many parameters"));
    } 
    
    @Test
    public void succesfulPage() {
        //Create the account to test
        TestUtility.post("/srv/register", "username=Spooky", "password=1234", "email=Cat@turtle.com", 
                         "day=27", "year=2000", "month=October");
        
        //Check normal fetch
        String response = TestUtility.fetch("/srv/user/Spooky");
        assertTrue(response.contains("Spooky"));
        
        //Test if case of username matters
        response = TestUtility.fetch("/srv/user/spooky");
        assertTrue(response.contains("Spooky"));
        
        //Check if / at end matters
        response = TestUtility.fetch("/srv/user/spooky/");
        assertTrue(response.contains("Spooky"));
    }
    
    @Test
    public void accountDoesNotExist() {
        String response = TestUtility.fetch("/srv/user/Spooky");
        assertTrue(response.contains("That account does not exist"));
    }
    
}
