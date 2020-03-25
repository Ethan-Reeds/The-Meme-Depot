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
    protected String username;
    protected String password;
    protected String email;
    protected String birthdate;
    protected String phone;
    
    protected int userID;       // is assigned not given
    protected static int nextID = 1000;

    
    protected boolean isAdmin;  // default is false you can set it to true
    protected byte[] avatar;    // default is to NULL;
    protected boolean loggedIn;
    
    public Account(String Username, String Password, String Email, String Year, String Month, String Day, String Phone){
        username = Username;        // check for xxxxx@xxxxx
        password = Password;
        email = Email;
        birthdate = Year + "-" + Month + "-" + Day;
        phone = Phone;
        isAdmin = false;
        avatar = null;
        
        userID = nextID;
        nextID++;
    }
    
    public int getUserID(){
        return userID;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public boolean getLoggedIn(){
        return loggedIn;
    }
    public void setLoggedIn(boolean status){
        loggedIn = status;
    }
    public String getPassword(){
        return password;
    }
    public boolean getIsAdmin(){
        return isAdmin;
    }
    public byte[] getAvatar(){
        return avatar;
    }
    public boolean setAvatar(byte[] newAvatar){
        avatar = newAvatar;
        return true;
    }
    public void setIsAdmin(boolean hasAdminPrivledges){
        isAdmin = hasAdminPrivledges;
    }
    
}





