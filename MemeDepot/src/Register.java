
import java.io.IOException;
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
public class Register extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var username = req.getParameter("username");    // username is email?
        var password = req.getParameter("password");
        if (username == null && password == null) {
            pw.println("No username or password provided");
        } else if (username == null && password != null){
            pw.println("No username provided");
        } else if (username != null && password == null) {
            pw.println("No password provided");
        } else {
            if( AccountManager.addUser(username, password) ){
                pw.println("Congrats you now have malware!");
                pw.println("Username:"+username);
                pw.println("Password:"+password);
                pw.println("True"); 
            }
            else{
                pw.println("Sorry bruh somethin aint quite right");
                pw.println("False");
            }
        }    
    }

}

