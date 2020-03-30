
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andy Riedlinger
 */

@WebServlet(urlPatterns={"/user/*"})
public class UserPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        
        //https://stackoverflow.com/questions/49547/how-do-we-control-web-page-caching-across-all-browsers
        //This page is dynamic do not cache it
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        resp.setHeader("Expires", "0"); // Proxies.
        PrintWriter output = resp.getWriter();
        
        //https://stackoverflow.com/questions/16965162/jetty-servlet-parameter-url-routing
        //Given url like localhost:2020/srv/user/Spooky
        //This contains /Spooky
        String pathInfo = req.getPathInfo();
        
        //No user given
        if(pathInfo == null) {
            doesNotExist(output);
            return;
        }
        
        String[] pathParams = pathInfo.split("/");
        
        //Too many parameters given
        if(pathParams.length != 2) {
            doesNotExist(output);
            return;
        }
        
        String userName = pathParams[1];

        Account account = AccountManager.instance.getAccount(userName);
        
        //Requested account does not exist
        if(account == null) {
            doesNotExist(output);
            return;
        }
        
        //Woo everything is good, let jsp handle the rest
        req.setAttribute("account", account);
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/user.jsp");
        dispatcher.forward(req, resp);
    }
    
    void doesNotExist(PrintWriter output) {
        output.write("That page does not exist");
    }
    
}
