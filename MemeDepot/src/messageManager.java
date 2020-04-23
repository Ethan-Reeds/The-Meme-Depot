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
    
    //protected Map<Account,Message> account_message_map = new HashMap<Account,Message>();
    // this one just keeps track of what messages associate with whichever account in no organized way
    // try to phase this one out
    
    protected static Map<String,ArrayList<Message> > message_list = new HashMap<String, ArrayList<Message>>();
    // this will map an account to all of the messages associated with that account
    
    public static boolean addMessage( Message message){
        // make sure both accounts exist, we know that the sender exist since theyre logged in
        Account sender = AccountManager.instance.getAccount(new SQLSearch(
                "username=?;", 
                new Object[]{message.sender}
        ));
        Account recipient = AccountManager.instance.getAccount(new SQLSearch(
                "username=?;", 
                new Object[]{message.recipient}
        ));
        if (sender != null){
            if (recipient != null){
              //https://stackoverflow.com/questions/7969286/java-how-to-add-values-to-array-list-used-as-value-in-hashmap/7969379
                // found the above usefull when ran into a problem
                if (message_list.get(message.sender) == null)
                    message_list.put(message.sender, new ArrayList<>());
                if (message_list.get(message.recipient) == null)
                    message_list.put(message.recipient, new ArrayList<>());
                message_list.get(message.sender).add(message);
                message_list.get(message.recipient).add(message);
                return true;
            }
            return false;
        }                            
         return false; 
    }
    
    public static ArrayList<Message> getMessages(Account a, Account b){
        // this gathers all of the messages between person a and b
        ArrayList<Message> mList = new ArrayList<>();
        for(Message m : message_list.get(a.getUsername())){
            if(m.sender.equals(a.getUsername()) && m.recipient.equals(b.getUsername())
                    || m.sender.equals(b.getUsername()) && m.recipient.equals(a.getUsername()))       // seeing if the sender or recipient of the message was from person b
                mList.add(m);
        }
        //sortMessages(mList,0,mList.size()-1);   // sort based on time incase it got messed up
        // sorting it makes it go in the wrong order, but i dont think we need it
        return mList;   // make sure that its sorting
    }
    
    public static void clearList(){
        message_list.clear();
    }

    // quick sort, might be useful in other areas as well
    public void  sortMessages(ArrayList<Message> list, int low, int high){
        if (low<high){
            int index = partition(list,low,high);
            sortMessages(list, low, index-1);
            sortMessages(list, index+1, high);
        }
    }
    public int partition(ArrayList<Message> list, int low, int high){
        Message pivot = list.get(high);
        int i = low-1;
        for (int j=low; j<high; j++){
            if(list.get(j).time_sort < pivot.time_sort){
                i++;
                Collections.swap(list,i,j);             
            }
        }
        Collections.swap(list, i+1, high);
       return i+1; 
    }
    
    
    
    
}
    