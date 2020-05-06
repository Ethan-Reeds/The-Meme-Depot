
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
    
    protected PrivacySetting privacy;
    
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
        
        privacy = PrivacySetting.Public;
        userID = nextID;
        nextID++;
        
        //Set default avatar
        try {
            BufferedImage bImage = ImageIO.read(new File("assets/default_avatar.png"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            avatar = bos.toByteArray();
        } catch(IOException e) {
            avatar = null;
        }
        
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
    
    public PrivacySetting getPrivacy() {
        return privacy;
    }
    
    public void setPrivacy(PrivacySetting privacy) {
        this.privacy = privacy;
    }
    
}





