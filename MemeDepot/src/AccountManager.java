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
    
    // https://www.geeksforgeeks.org/java-util-hashmap-in-java-with-examples/
    // https://www.geeksforgeeks.org/hashmap-containskey-method-in-java/
    // https://www.geeksforgeeks.org/hashmap-get-method-in-java/
    // https://www.geeksforgeeks.org/hashmap-put-method-in-java/
    protected static Map<String, Account> accountList = new HashMap<String,Account>();
    
    static AccountManager instance = new AccountManager();  // instance used for the integreation testing,
                                                            // called in /clear servlet
    
    
    public static boolean login(String username, String password) {
        password = encryptThisString(password);
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
        password = encryptThisString(password);
        // if username exists
        if(accountList.containsKey(username)) {
            // if password matches username
            if(accountList.get(username).getPassword().equals(password)) {
                // user exists and info matches
                return true;
            }
        }
        // user doesn't exist or info doesn't match
        return false;
    }
    
    public int getUID(String username){
        // all users have an ID so if you get back -1 it means that the user is not in the database
        if (accountList.containsKey(username))
            return accountList.get(username).getUserID();
        return -1;      // if user does not exist, wont let me return null or i would
    }
    
    ///Returns null if no email
    public String getEmail(String username){
        String email = accountList.get(username).getEmail();
        return email;
        // since all usernames must be unique checking the username is all that is needed?
        
        // https://stackoverflow.com/questions/40693845/hashmap-java-get-value-if-it-exists
        // used this to see how to see if the key exists using .get() function
    }
    
    public static boolean addUser(String username, String password, String email, String year, String month, String day, String phone){
        if (accountList.containsKey(email)) {
            return false;
        }
        password = encryptThisString(password);
        accountList.put(email, new Account(username, password, email, year, month, day, phone));
        return true; 
    }
    //ripped from https://www.geeksforgeeks.org/sha-512-hash-in-java/?ref=rp
    public static String encryptThisString(String input) 
    { 
        try { 
            // getInstance() method is called with algorithm SHA-512 
            MessageDigest md = MessageDigest.getInstance("SHA-512"); 
  
            // digest() method is called 
            // to calculate message digest of the input string 
            // returned as array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
  
            // Add preceding 0s to make it 32 bit 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
  
            // return the HashText 
            return hashtext; 
        } 
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
  
    public static ArrayList<String> searchForUser(String search){
        //Looks through the HashMap for a username that best matches the search 
        
        ArrayList<String> bestMatchs = new ArrayList<String>();
        for (String user : accountList.keySet()) {
           if (user.matches("(.*)"+search+"(.*)")) {
               bestMatches.add(user);
           }
        } 
        return bestMatches; 
    }
    
//     public  getUser(String username){
//         //https://www.geeksforgeeks.org/iterate-map-java/
//         // used this to see how to use the forEach() method
//         // turns out you cant return out of the forEach method for maps
//         // so Andy showed me an example of using a for each loop to go through a map
//         return getAccount(username); // wouldn't the get user function return the account instead of the username as it is already taking that as a parameter. 
        
//     } i don't think we would  need this method. 
    
    public boolean isAdmin(String username){
        Account acct = getAccount(username);
        if (acct != null)
            return acct.getIsAdmin();
        return false;
    }
    
    public byte[] getAvatar(String username){
        Account acct = getAccount(username);
        if (acct != null)
            return acct.getAvatar();
        return null;
    }
    
    public boolean setAvatar(String username, byte[] img ){
        Account acct = getAccount(username);
        if (acct != null)
            return acct.setAvatar(img);
        return false;
    }    
    
    private Account getAccount(String username){
        if (accountList.containsKey(username)) {
          return accountList.get(username);  
        } 
        return null;
    }
    
    static void clear() {
        accountList.clear(); 
    }
}
