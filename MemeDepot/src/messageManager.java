/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author Ethan Reeds
 */
public class messageManager {
    
    protected Map<Account,Message> account_message_map = new HashMap<Account,Message>();
    // this one just keeps track of what messages associate with whichever account in no organized way
    
    protected Map<Account,ArrayList<Message> > message_list = new HashMap<Account, ArrayList<Message>>();
    // this will map an account to all of the messages associated with that account
    
    public boolean addMessage(Account sender, Message message){
        // make sure both accounts existm, we know that the sender exist since theyre logged in
        if (AccountManager.accountList.get(message.recipient.getUsername()) != null ){
            account_message_map.put(sender, message);
            // at this point the message is only being associated with the sender
            return true;
        }                            
         return false; 
    }
    
    public ArrayList<Message> getMessages(Account acct){
        ArrayList<Message> mList = new ArrayList<>();
        for ( Account a : account_message_map.keySet()){
            mList.add(account_message_map.get(a));
        }
        return sortMessages(mList);
    }
    
    public ArrayList<Message> sortMessages(ArrayList<Message> list){
        // use quick sort on with the timestamps
        return null;
    }
    
}
