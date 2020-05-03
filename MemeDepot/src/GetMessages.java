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
import java.util.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns={"/getmessages"})
@MultipartConfig
public class GetMessages {
    public void doGet(HttpServletRequest req, 
    HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var sess = req.getSession();
        var name = sess.getAttribute("username");
        Account currentUser = AccountManager.getAccount(name.toString());
        HashMap<String, ArrayList<Message>> messages = currentUser.getAllMessages();
        for (String user : messages.keySet()) {
           pw.write("#########################");
           String newData = user + "\n";
           for (Message message : messages.get(user)) {
               String newMessgStr = message.sender + ":" + message.message;
               pw.write(newMessgStr);
               pw.write("\n");
           }
        }
    }
}