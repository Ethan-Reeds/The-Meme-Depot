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
    static String sqlURL = "jdbc:mysql://localhost:3306/memedepot";
    static String sqlDriver = "com.mysql.jdbc.Driver";
    static String sqlUser = "root";
    
    public static boolean login(String username, String password) {
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            var query = ParameterizedStatement.executeOneQuery(conn, 
                    "SELECT username, password FROM users WHERE username=? AND pass=?", 
                    username, password).getAll();
            if(!query.isEmpty()) {
                // user exists
                var update = ParameterizedStatement.executeOneUpdate(conn, 
                        "SELECT loggedIn FROM users WHERE username=?", 
                        username);
                if(update == 0) {
                    // not logged in yet, log them in
                    ParameterizedStatement.executeOneUpdate(conn, 
                            "UPDATE users SET loggedIn=1 WHERE username=?", 
                            username);
                    return true;
                }
                else {
                    // already logged in
                    return false;
                }
            }
            else {
                // user does not exist
                return false;
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static boolean logout(Account acc){
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            var update = ParameterizedStatement.executeOneUpdate(conn, 
                    "SELECT loggedIn FROM users WHERE userID=?", 
                    acc.getUserID());
                if(update == 1) {
                    // logged in, log them out
                    ParameterizedStatement.executeOneUpdate(conn, 
                            "UPDATE users SET loggedIn=1 WHERE userID=?", 
                            acc.getUserID());
                    return true;
                }
                else {
                    // not logged in
                    return false;
                }
            } catch(SQLException ex) {
                throw new RuntimeException(ex);
            }
    } 
    
    public static boolean verifyUser(String username, String password) {
        // returns true if username and password match account in database
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            var query = ParameterizedStatement.executeOneQuery(conn, 
                    "SELECT username, password FROM users WHERE username=? AND password=?", 
                    username, password).getAll();
            if(query.isEmpty()) {
                // no matching data
                return false;
            }
            return true;
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public int getUID(Account acc){
        // returns null if username doesn't exist
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            var update = ParameterizedStatement.executeOneUpdate(conn, 
                    "SELECT userID FROM users WHERE username=?", 
                    acc.getUsername());
            return update;
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public String getEmail(Account acc){
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            var query = ParameterizedStatement.executeOneQuery(conn, 
                    "SELECT email FROM users WHERE userID=?",
                    acc.getUserID()).getAll();
            if(query.isEmpty()) {
                // no matching data
                return null;
            }
            else {
                // return result as a string
                return query.toString();
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static boolean addUser(String username, String password, String email, String year, String month, String day){
        // userID is auto incremented
        // admin is 0 by default
        // avatar is null by default
        String birthday = year+"-"+month+"-"+day;
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            var query = ParameterizedStatement.executeOneQuery(conn, 
                    "SELECT * FROM users WHERE username=? AND email=?", 
                    username, email).getAll();
            // if username or email doesn't already exist
            if(query.isEmpty()) {
                // add to database
                ParameterizedStatement.executeOneUpdate(conn, 
                        "INSERT INTO users(username, password, email, birthday)"
                                + " VALUES(?,?,?,STR_TO_DATE(?, '%y,%m,%d')", 
                        username, password, email, birthday);
                // add corresponding account
                new Account(username, password, email, year, month, day);
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
    
    public String getUser(Account acc){
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            // returns null if userID doesn't exist
            var query = ParameterizedStatement.executeOneQuery(conn, 
                    "SELECT username FROM users WHERE userID=?", 
                    acc.getUserID()).getAll();
            if(query.isEmpty()) {
                // no matching data
                return null;
            }
            // return data as string
            return query.toString();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public boolean isAdmin(Account acc){
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            // returns 1 if admin, 0 if not admin
            var query = ParameterizedStatement.executeOneQuery(conn, 
                    "SELECT userID FROM users WHERE userID=? AND admin=1", 
                    acc.getUserID()).getAll();
            return query.size() > 0;
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /*
    public byte[] getAvatar(String username){
        // convert blob to byte[]?
    }
    */

    public void setAvatar(Account acc, byte[] img ){
        // update avatar column for user
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            ParameterizedStatement.executeOneUpdate(conn, 
                    "UPDATE users SET avatar=? WHERE userID=?", img, acc.getUserID());
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }    
    
    static void clear() {
        try(var conn = java.sql.DriverManager.getConnection(sqlURL)) {
            ParameterizedStatement.executeOneUpdate(conn,
                    // basically deletes table and remakes it empty
                    "TRUNCATE TABLE users");
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
