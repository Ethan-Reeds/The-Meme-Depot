import java.io.IOException;
import java.util.HashSet;
import javax.servlet.annotation.MultipartConfig;
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
 * @author Ethan Reeds
 */

@WebServlet(urlPatterns={"/register"})
@MultipartConfig
public class Register extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var sess = req.getSession();
        var username = req.getParameter("username");
        var password = req.getParameter("password");
        var email = req.getParameter("email");
        var year = req.getParameter("year");
        var month = req.getParameter("month");
        var day = req.getParameter("day");
        var phone = req.getParameter("phone");
        
        if(email != null) {
            String[] parts = email.split("@");
            
            if(parts.length != 2 || parts[0].isEmpty()) {
                pw.println("Improper email");
                pw.println("False");
                return;
            }
        }
        
        if(AccountManager.addUser(username, password, email, year, month, day)) {   // checks for missing parameters and duplicates
            // add new account object
            sess.setAttribute("account", new Account(username, password, email, year, month, day));
            pw.println("Congrats you now have malware!");
            pw.println("Username:"+username);
            pw.println("Password:"+password);
            pw.println("<br>");
            pw.println("True"); 
                
        } else {
                pw.println("Sorry bruh somethin aint qutite right");
                pw.println("False");
            }
    }

}



