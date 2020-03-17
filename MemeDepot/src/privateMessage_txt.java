
/**
 *
 * @author Ethan Reeds/Jim
 * Ripped this from the example in class
 */

import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns={"/pm_t"})
@MultipartConfig
public class privateMessage_txt {
    public void doPost(HttpServletRequest req, 
    HttpServletResponse resp) throws IOException{
        // building the message/adding the message to manager
        String from = req.getParameter("from");
        String to = req.getParameter("to");     // handle input errors with jquery
        String msg = req.getParameter("message");
        Account a = AccountManager.accountList.get(from);
        Account b = AccountManager.accountList.get(to);
        Message msgObj = new Message(msg, a, b);
        messageManager.addMessage(msgObj);
        
        //getting the message list back to display
        ArrayList<Message> mList = new ArrayList<>();
        mList = messageManager.getMessages(a, b);
        
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        for (Message m : mList){
            pw.print(m.message);
        }
    }
}
