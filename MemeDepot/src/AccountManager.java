/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.lang.String;
import java.util.function.BiConsumer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ethan Reeds
 */
public class AccountManager {

    static AccountManager instance = new AccountManager();  // instance used for the integreation testing,
                                                            // called in /clear servlet
    
    // MySQL database connection info
    static String sqlURL = "jdbc:mysql://localhost:3306/memedepot?useSSL=false";
    static String sqlDriver = "com.mysql.jdbc.Driver";
    static String sqlUser = "root";
    static String sqlPass = "HhLlHh@972";
    
    public AccountManager() {}
    
    public Account getAccount(SQLSearch search) {
        try(var conn = java.sql.DriverManager.getConnection(sqlURL, sqlUser, sqlPass)) {
            String querystring = "SELECT * FROM users WHERE " + search.query;
            var result = new ArrayList<Account>();
            for(var item : ParameterizedStatement.executeOneQuery(conn, querystring, search.params)) {
                result.add(Account.fromSQL(item));
            }
            if(result.size() > 0) {
                return result.get(0);
            }
            else {
                return null;
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static boolean login(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
                // missing parameter(s)
                return false;
        }
        boolean login = false;
        if(AccountManager.verifyUser(username, password)) {
            // username and password match
            try(var conn = java.sql.DriverManager.getConnection(sqlURL, sqlUser, sqlPass)) {
                var query = AccountManager.instance.getAccount(new SQLSearch(
                        "username=? AND loggedIn=0;", 
                        new Object[]{username}
                ));
                if(query != null) {
                    // not logged in yet, log them in
                    ParameterizedStatement.executeOneUpdate(conn, 
                            "UPDATE users SET loggedIn=1 WHERE username=?;", 
                            username);
                    login = true;
                }
            } catch(SQLException ex) {
                throw new RuntimeException(ex);
            }
            // update Account object
            Account user = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{username, password}
        ));
            user.loggedIn = true;
        }
        return login;
    }
    
    public static boolean logout(Account acc){
        boolean loggedOut = false;   // returns false if account doesn't exist
        try(var conn = java.sql.DriverManager.getConnection(sqlURL, sqlUser, sqlPass)) {
            var query = AccountManager.instance.getAccount(new SQLSearch(
                    "userID=? AND loggedIn=1;", 
                    new Object[]{acc.getUserID()}
            ));
            if(query != null) {
                // logged in already, log them out
                ParameterizedStatement.executeOneUpdate(conn, 
                        "UPDATE users SET loggedIn=0 WHERE userID=?;", 
                        acc.getUserID());
                acc.loggedIn = false;
                loggedOut = true;
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return loggedOut;
    } 
    
    public static boolean verifyUser(String username, String password) {
        // returns true if username and password match existing account
        boolean result = false;
        Account user = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{username, password}
        ));
        if(user != null) {
            // found matching account
            result = true;
        }
        return result;
    }
    
    public int getUserID(Account acc){
        int result = -1;   // returns -1 if account doesn't exist
        Account user = AccountManager.instance.getAccount(new SQLSearch(
                "username=?;", 
                new Object[]{acc.getUsername()}
        ));
        result = user.getUserID();
        return result;
    }
    
    public String getEmail(Account acc){
        String result = null;   // returns null if account doesn't exist
        Account user = AccountManager.instance.getAccount(new SQLSearch(
                "userID=?;", 
                new Object[]{acc.getUserID()}
        ));
        result = user.getEmail();
        return result;
    }
    
    public static boolean addUser(String username, String password, String email, String year, String month, String day){
        // userID is auto incremented
        // admin is 0 by default
        // avatar is null by default
        // adds corresponding Account object in Login class
        String birthday = year+"-"+month+"-"+day;
        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || 
                year.isEmpty() || month.isEmpty() || day.isEmpty()) {
            // missing parameter(s)
            return false;
        }
        try(var conn = java.sql.DriverManager.getConnection(sqlURL, sqlUser, sqlPass)) {
            var query = AccountManager.instance.getAccount(new SQLSearch(
                    "username=? OR email=?;", 
                    new Object[]{username, email}
            ));
            // if username or email doesn't already exist
            if(query == null) {
                // add to database
                ParameterizedStatement.executeOneUpdate(conn, 
                        "INSERT INTO users(username, password, email, birthday)"
                                + " VALUES(?,?,?,?);", 
                        username, password, email, birthday);
                return true;
            }
            else {
                // username or email already exists
                return false;
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public boolean isAdmin(Account acc){
        boolean result = false;
        Account user = AccountManager.instance.getAccount(new SQLSearch(
                "userID=? AND admin=1;", 
                new Object[]{acc.getUserID()}
        ));
        if(user != null) {
            // user is admin
            result = true;
        }
        return result;
    }
    
    public boolean setAdmin(Account acc, boolean status) {
        // update admin column for user in database
        try(var conn = java.sql.DriverManager.getConnection(sqlURL, sqlUser, sqlPass)) {
            ParameterizedStatement.executeOneUpdate(conn, 
                    "UPDATE users SET admin=? WHERE userID=?;", status, acc.getUserID());
            // update avatar variable in Account object
            acc.isAdmin = status;
            
            return true;
            
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public byte[] getAvatar(Account acc){
        byte[] result = null;   // returns null if account doesn't exist
        Account user = AccountManager.instance.getAccount(new SQLSearch(
                "userID=?;", 
                new Object[]{acc.getUserID()}
        ));
        result = user.getAvatar();
        return result;
    }

    public boolean setAvatar(Account acc, byte[] img ){
        // update avatar column for user in database
        try(var conn = java.sql.DriverManager.getConnection(sqlURL, sqlUser, sqlPass)) {
            ParameterizedStatement.executeOneUpdate(conn, 
                    "UPDATE users SET avatar=? WHERE userID=?;", img, acc.getUserID());
            // update avatar variable in Account object
            acc.avatar = img;
            
            return true;
            
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }    
    
    static void clear() {
        try(var conn = java.sql.DriverManager.getConnection(sqlURL, sqlUser, sqlPass)) {
            ParameterizedStatement.executeOneUpdate(conn,
                    // deletes everything from users table
                    "DELETE FROM users WHERE userID IS NOT NULL;");
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
