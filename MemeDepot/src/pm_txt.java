
/**
 *
 * @author Ethan Reeds/Jim
 * Ripped this from the example in class
 */

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns={"/pm_txt"})
@MultipartConfig
public class pm_txt extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var sess = req.getSession();
        
        // building the message/adding the message to manager
        
        String from = sess.getAttribute("username").toString(); // no idea if this will work
        String to = req.getParameter("to");     // handle input errors with jquery
        String msg = req.getParameter("message");
        
        // error handling
        if (from == null || to == null)
            pw.println("false");    // this is for testing purposes
            // might be able to handle this in the jquery 
        else if (msg == null)
            pw.println("false");
        else{
            Account a = AccountManager.accountList.get(from);
            Account b = AccountManager.accountList.get(to);
            Message msgObj = new Message(msg, a, b);
            messageManager.addMessage(msgObj);

            //getting the message list back to display
            ArrayList<Message> mList = new ArrayList<>();
            mList = messageManager.getMessages(a, b);


            for (Message m : mList){
                pw.println(m.message);
            }
        }
        
        
    }
}
