
/**
 *
 * @author Ethan Reeds/Jim
 * Ripped this from the example in class
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns={"/pm_img"})
@MultipartConfig
public class pm_img extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        
        var from = req.getParameter("from"); 
        var to = req.getParameter("to");     // handle input errors with jquery
        var msg_in = req.getPart("message");
        var msgContent = msg_in.getInputStream();
        byte[] msg_out;
        
        // error handling
        if (from == null || to == null)
            pw.println("Username(s) missing");    // this is for testing purposes
            // might be able to handle this in the jquery 
        else if (msg_in == null)
            pw.println("No message");
        else{
            Account a = AccountManager.getAccount(from);
            Account b = AccountManager.getAccount(to);
            if(a == null || b == null)
                pw.println("Account(s) do not exist");    // makes sure accounts are valid
            else{
                    ////////////////////// BYTE ARRAY STUFF FOR IMG
                    var bos = new ByteArrayOutputStream();
                    byte[] bee = new byte[1000];
                    while(true){
                        int numRead = msgContent.read(bee);
                        if( numRead <= 0 )
                            break;
                        bos.write(bee,0,numRead);
                        if( bos.size() > 100000 ){
                            //prevent DoS attack... what jim said
                            resp.sendError(500,"Too much data");
                            return;
                        }
                    }
                    msg_out = bos.toByteArray();
                ///////////////////////
                // everything esle should be the same as it was for a txt message
                
                
                Message msgObj = new Message(msg_out, a, b);
                messageManager.addMessage(msgObj);

                //getting the message list back to display
                ArrayList<Message> mList;// = new ArrayList<>();
                mList = messageManager.getMessages(a, b);


                for (Message m : mList){
                    pw.println(m.message);
                }
            }
        }
        
        
    }
}
