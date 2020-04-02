
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author techn
 */
public class TestUtility {
    static String fetch(String... allurls) {
        try {
            String str = null;
            byte[] returnedData = new byte[]{0};  //dummy
            for (String oneurl : allurls) {
                var url = new URL("http://localhost:2020" + oneurl);
                var conn = url.openConnection();
                conn.connect();
                var istr = conn.getInputStream();
                returnedData = istr.readAllBytes();
            }
            return new String(returnedData, 0, returnedData.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    //Takes one url, and then a vargs of parameters such as id=10, name=Steve, ...
    //then puts it all in a post request and sends it and returns response
    static String post(String path, String... parameters) {
        try {
            String str = null;
            byte[] returnedData = new byte[]{0};  //dummy
            
            //Setup connection
            URL url = new URL("http://localhost:2020" + path);
            URLConnection conn = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            
            //Setup data
            //Put the parameter strings together
            StringJoiner sj = new StringJoiner("&");
            
            for(String parameterSet : parameters) {
                //This isn't encoding which is sorta bad but I was too lazy to make it
                sj.add(parameterSet);
            }
            
            byte[] postData = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = postData.length;
            
            //Setup output
            http.setFixedLengthStreamingMode(length);
            //http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            
            //Actually insert our data
            http.getOutputStream().write(postData);
            
            //Get the info returned
            var response = conn.getInputStream();
            returnedData = response.readAllBytes();
            
            //Parse it into a string and return
            return new String(returnedData, 0, returnedData.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
