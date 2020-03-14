
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ethan Reeds
 */
public class Message {
    // objective of the message object
    // contain the contents of a message that will be associated with a user
    public byte[] img;
    public String message;
    public Account sender;
    public Account recipient;
    public Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // for organizing the messages
    
    
    
}
