
import java.io.IOException;
import javax.servlet.ServletException;
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
 * @author techn
 */

@WebServlet(urlPatterns={"/privacy"})
@MultipartConfig
public class PrivacySettings extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var sess = req.getSession();
        
        String user = req.getParameter("username");
        String settingValue = req.getParameter("setting");
        
        String activeUser = (String) sess.getAttribute("username");
        
        if(activeUser == null || !activeUser.equalsIgnoreCase(user)) {
            pw.print("Not logged in.");
            return;
        }
        
        Account account = AccountManager.instance.getAccount(user);
        
        if(account == null) {
            pw.print("Invalid Username");
            return;
        }
        
        try {
            PrivacySetting setting = PrivacySetting.valueOf(settingValue);
            account.setPrivacy(setting);
            pw.print("Success");
        } catch(IllegalArgumentException e) {
            pw.print("That privacy value does not exist");
        }
        
    }
    
}
