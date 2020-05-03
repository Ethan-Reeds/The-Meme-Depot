
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
    public String sender;
    public String recipient;
    public Timestamp timestamp; // for organizing the messages
    public long time_sort;  // used for quick sort sorting
    
    public Message(byte[] data,String from,String to){
        message = null;
        img = data;
        sender = from;
        recipient = to;
        timestamp = new Timestamp(System.currentTimeMillis());
        time_sort = timestamp.getTime();       
    }
    public Message(String data,String from,String to){
        img = null;
        message = data;
        sender = from;
        recipient = to;
        timestamp = new Timestamp(System.currentTimeMillis());
        time_sort = timestamp.getTime();       
    }    
}
