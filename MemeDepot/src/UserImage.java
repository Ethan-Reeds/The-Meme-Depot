
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author techn
 */
@WebServlet(urlPatterns={"/image/*"})
public class UserImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        
        //https://stackoverflow.com/questions/49547/how-do-we-control-web-page-caching-across-all-browsers
        //This page is dynamic do not cache it
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        resp.setHeader("Expires", "0"); // Proxies.
        
        //https://stackoverflow.com/questions/16965162/jetty-servlet-parameter-url-routing
        //Given url like localhost:2020/srv/image/Spooky
        //This contains /Spooky
        String pathInfo = req.getPathInfo();
        
        //No image given
        //First part accounts for /image Second for /image/
        if(pathInfo == null || pathInfo.length() == 1) {
            resp.getWriter().write("No parameters given");
            return;
        }
        
        String[] pathParams = pathInfo.split("/");
        
        //Too many parameters given
        //Second part accounts for a / with nothing after it not being an error
        if(pathParams.length > 2 && !pathParams[2].isEmpty()) {
            resp.getWriter().write("Too many parameters");
            return;
        }
        
        String userName = pathParams[1];
        
        Account account = AccountManager.instance.getAccount(userName);
        
        //Requested account does not exist
        if(account == null) {
            resp.getWriter().write("That image does not exist");
            return;
        }

        resp.setContentType("image/png");
        var output = resp.getOutputStream();
        byte[] image = account.getAvatar();        
        output.write(image, 0, image.length);
    }
    
}
