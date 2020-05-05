import java.util.*;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ethan Reeds
 * TODO make testing harness for this
 */
public class Account {
    protected static String[] fields = new String[]{
        // all columns in users table in database
        "userID", "email", "username", "password", "birthday", "admin", 
        "avatar", "loggedIn"};
    
    protected String username;
    protected String password;
    protected String email;
    protected String birthdate;
    protected int userID;       // auto-increments in database
    protected boolean isAdmin;  // default is false you can set it to true
    protected byte[] avatar;    // default is to NULL;
    protected boolean loggedIn;
    
    public Account(String Username, String Password, String Email, String Year, 
            String Month, String Day){
        this.username = Username;        // check for xxxxx@xxxxx
        this.password = Password;
        this.email = Email;
        this.birthdate = Year + "-" + Month + "-" + Day;
        this.isAdmin = false;
        this.avatar = null;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            var update = ParameterizedStatement.executeOneUpdate(conn, 
                    "SELECT userID FROM users WHERE username=?;", 
                    this.username);
            // sets userID to auto-incremented int from database
            this.userID = update;
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static Account fromSQL(Map<String, ParameterizedStatement.Value> M) {
        return new Account(
            M.get("username").asString(),
            M.get("password").asString(),
            M.get("email").asString(),
            M.get("birthday").asString().substring(0, 5),   // year xxxx
            M.get("birthday").asString().substring(6, 8),   // month xx
            M.get("birthday").asString().substring(9)       // day xx
        );
    }
    
    public int getUserID(){
        return this.userID;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public boolean getLoggedIn(){
        return this.loggedIn;
    }
    public void setLoggedIn(boolean status){
        this.loggedIn = status;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            // update status in database
            ParameterizedStatement.executeOneUpdate(conn,
                    "UPDATE users SET loggedIn=? WHERE userID=?",
                    status, this.userID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public String getPassword(){
        return this.password;
    }
    public boolean getIsAdmin(){
        return this.isAdmin;
    }
    public byte[] getAvatar(){
        return this.avatar;
    }
    public boolean setAvatar(byte[] newAvatar){
        this.avatar = newAvatar;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            // update avatar in database
            ParameterizedStatement.executeOneUpdate(conn,
                    "UPDATE users SET avatar=? WHERE userID=?",
                    newAvatar, this.userID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return true;
    }
    public void setIsAdmin(boolean hasAdminPrivledges){
        this.isAdmin = hasAdminPrivledges;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            // update admin in database
            ParameterizedStatement.executeOneUpdate(conn,
                    "UPDATE users SET admin=? WHERE userID=?",
                    hasAdminPrivledges, this.userID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}





