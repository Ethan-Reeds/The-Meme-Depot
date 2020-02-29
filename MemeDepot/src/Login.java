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
        //var name = req.getParameter("name");
        var username = req.getParameter("username");
        var password = req.getParameter("password");
        req.getSession().removeAttribute("username");
        if( password == null || username == null ){
            pw.printf("Null");
        } else {
            
            if (sess.getAttribute(username) != null){
                pw.printf("False");
            }
            else if (AccountManager.login(username,password)){
                sess.setAttribute("username", username );
                pw.printf("True");
            }
            else{
                pw.println("False");
            }
        }
    }

}
