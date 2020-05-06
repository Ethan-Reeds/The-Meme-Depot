
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author techn
 */
@WebServlet(urlPatterns={"/browse"})
public class Browse extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var pw = resp.getWriter();
        String s_offset = req.getParameter("offset");
        String s_numToLoad = req.getParameter("loadNum");
        
        JSONObject json = new JSONObject();
        
        if(s_numToLoad == null || s_numToLoad.isEmpty()) {     
            json.put("Error", "No amount to load.");
            pw.print(json.toString());
            return;
        }
        
        int numToLoad = 0;
        Integer.parseInt(s_numToLoad);
        int offset = 0;
        
        try {
            numToLoad = Integer.parseInt(s_numToLoad);
            if(s_offset != null) {      
                offset = Integer.parseInt(s_offset);
            }
        } catch(NumberFormatException e) {
            json.put("Error", "Malformatted number");
            pw.print(json.toString());
            return;
        }
        
        JSONArray users = new JSONArray();
        
        Account[] accounts = AccountManager.instance.getUsers();
        for(int i = offset; i < offset + numToLoad; i++) {
            if(i >= accounts.length)
                break;
            users.add(accounts[i].getUsername());
        }
        
        json.put("Error", null);
        json.put("Users", users);
        
        pw.print(json.toString());
    }
    
}
