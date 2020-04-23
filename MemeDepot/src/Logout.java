import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns={"/logout"})
public class Logout extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var sess = req.getSession();
        var sess_username = sess.getAttribute("username");
        var sess_password = sess.getAttribute("password");
        
        var account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{sess_username, sess_password}
        ));

        if (AccountManager.logout(account)) {
            account.loggedIn = false;
            sess.removeAttribute("username");
            sess.removeAttribute("password");
            sess.removeAttribute("account");
            pw.print("True");
            pw.printf("Logged out");
        }
        else{
            pw.print("False");
        }
    }
}
