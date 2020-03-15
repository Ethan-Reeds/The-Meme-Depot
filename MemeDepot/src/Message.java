
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
    public Timestamp timestamp; // for organizing the messages
    public long time_sort;  // used for quick sort sorting
    
    public Message(byte[] data,Account a,Account b){
        message = null;
        img = data;
        sender = a;
        recipient = b;
        timestamp = new Timestamp(System.currentTimeMillis());
        time_sort = timestamp.getTime();       
    }
    public Message(String data,Account a,Account b){
        img = null;
        message = data;
        sender = a;
        recipient = b;
        timestamp = new Timestamp(System.currentTimeMillis());
        time_sort = timestamp.getTime();       
    }
    
    
    
}
