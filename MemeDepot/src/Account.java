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
    protected String username;   // should be email address
    protected String password;
    protected int userID;       // is assigned not given
    protected static int nextID = 1000;
    protected boolean isAdmin;  // default is false you can set it to true
    protected byte[] avatar;    // default is to NULL;
    protected boolean loggedIn;
    
    public Account(String usrName, String pswrd){
        username = usrName;        // check for xxxxx@xxxxx
        password = pswrd;
        isAdmin = false;
        avatar = null;        
        userID = nextID;
        nextID++;
    }    
    public String getUsername(){
        return username;
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
    public int getUserID(){
        return userID;
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
