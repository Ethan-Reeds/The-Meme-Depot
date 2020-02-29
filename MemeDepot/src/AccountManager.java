/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.lang.String;
import java.util.function.BiConsumer;

/**
 *
 * @author Ethan Reeds
 */
public class AccountManager {
    
    
    protected static Map<String, Account> accountList = new HashMap<String,Account>();
    
    static AccountManager instance = new AccountManager();  // instance used for the integreation testing, called in /clear servlet
    
    
    public static boolean login(String username, String password) {
        if (accountList.isEmpty() || !accountList.containsKey(username)){
            return false;
        } 
        if (!accountList.get(username).getLoggedIn()){
            accountList.get(username).setLoggedIn(true);
        }
        else{
            return false;
        }
        
        return (accountList.get(username).getPassword().equals(password));
    }
    
    public static boolean logout(String username){
        if ( accountList.get(username).getLoggedIn() == true ){
            accountList.get(username).setLoggedIn(false);
            return true;
        }
        return false;
    } 
    
    public static boolean verifyUser(String username, String password){
        return (accountList.get(username) != null);
        // since all usernames must be unique checking the username is all that is needed?
        
        // https://stackoverflow.com/questions/40693845/hashmap-java-get-value-if-it-exists
        // used this to see how to see if the key exists using .get() function
    } 
    public static boolean addUser(String emailAddress, String Password){
        if (emailAddress.split("@").length != 2){
            return false;
        }
        if (!accountList.isEmpty()){
            if (!verifyUser(emailAddress, Password)){
                accountList.put(emailAddress, new Account(emailAddress,Password));
                return true;
            }
            return false;
        }
        accountList.put(emailAddress, new Account(emailAddress,Password));
        return true;
    }
    public int getUID(String username){
        // all users have an ID so if you get back -1 it means that the user is not in the database
        if (accountList.get(username) != null)
            return accountList.get(username).getUserID();
        return -1;      // if user does not exist, wont let me return null or i would
    }
    public String getEmail(int userID){
        //https://www.geeksforgeeks.org/iterate-map-java/
        // used this to see how to use the forEach() method
        // turns out you cant return out of the forEach method for maps 
        // so Andy showed me an example of using a for each loop to go through a map
        Account acct = getAccount(userID);
        if (acct != null)
            return acct.getUsername();
        return null;
    }
    public boolean isAdmin(int userID){
        Account acct = getAccount(userID);
        if (acct != null)
            return acct.getIsAdmin();
        return false;
    }
    
    public byte[] getAvatar(int userID ){
        Account acct = getAccount(userID);
        if (acct != null)
            return acct.getAvatar();
        return null;
    }
    
    public boolean setAvatar(int userID, byte[] img ){
        Account acct = getAccount(userID);
        if (acct == null){
            return false;
        }
        return acct.setAvatar(img);
    }    
    
    private Account getAccount(int userID){
        for( Account a : accountList.values()){
            if (a.getUserID() == userID){
                return a;
            }
        }
        return null;
    }
    static void clear() {
        accountList.clear(); 
        Account.nextID = 1000; 
    }
}