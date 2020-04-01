import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns={"/login"})
public class Login extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var sess = req.getSession();
        var username = req.getParameter("username");
        var password = req.getParameter("password");
        if( password == null || username == null ){
            pw.printf("Null");
        } else {
            
            if (sess.getAttribute(username) != null || !AccountManager.verifyUser(username, password)){
                // checks to see if you're already logged in or have an account
                pw.printf("False");
            }
            else if (AccountManager.login(username,password)){
                sess.setAttribute("username", username );
                pw.println("You are now logged in as:"+username);
                pw.printf("True");
            }
            else{
                // necessary for user input resasons
                pw.println("IDK mane something went wrong, give it another go");
                pw.println("False");
            }
        }
    }

}
