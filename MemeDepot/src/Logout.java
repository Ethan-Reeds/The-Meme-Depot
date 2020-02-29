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
        var username = req.getParameter("username");
        
        if (username == null)
            pw.print("False");
        else if (AccountManager.logout(username)){
            req.getSession().removeAttribute("username");
            pw.print("True");
        }
        else{
            pw.print("False");
        }
        pw.printf("Logged out");
    }

}
