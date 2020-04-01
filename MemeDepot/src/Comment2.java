import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
/**
 *
 * @author cayle
 */
@WebServlet(urlPatterns={"/comment"})
public class Comment2 extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var sess = req.getSession();
        var comment = req.getParameter("comment");
        var username = sess.getAttribute("username");
        var password = sess.getAttribute("password");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(username == null && password == null){
            //Checking if your already logged in
            pw.printf("Not logged in");
        }
        else if(username == null && password != null){
            pw.printf("No username");
        }
        else if(username == null && password != null){
            pw.printf("No password");
        }
        else if(comment == null){
            //Check if there is a message
            pw.printf("No item being posted.");
        }
        else{
            CommentManager.addComment(comment);
            pw.printf(username + " At "+ timestamp + " posted: " + comment );
        }
    }
}
