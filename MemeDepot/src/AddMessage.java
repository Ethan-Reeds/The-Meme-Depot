/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Makarand
 */
import java.io.IOException;
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

@WebServlet(urlPatterns={"/addmessage"})
@MultipartConfig
public class AddMessage extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var sess = req.getSession();
        var username = sess.getAttribute("username");
        if (username == null) {
            pw.printf("False");
            return;
        }
        var to = req.getParameter("to");
        if (AccountManager.getAccount(to) == null) {
            pw.printf("False");
            return;
        }
        var message = req.getParameter("message");
        Account currentUser = AccountManager.getAccount(username.toString());
        currentUser.addNewMessage(username.toString(), to.toString(), message.toString());
        pw.printf("True");
    }

}

