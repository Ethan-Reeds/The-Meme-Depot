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
    
    protected Map<Account,ArrayList<Message> > message_list = new HashMap<Account, ArrayList<Message>>();
    // this will map an account to all of the messages associated with that account
    
    public boolean addMessage( Message message){
        // make sure both accounts exist, we know that the sender exist since theyre logged in
        if (AccountManager.accountList.containsKey(message.sender.getUsername())){
            if (AccountManager.accountList.containsKey(message.recipient.getUsername())){
                //  https://stackoverflow.com/questions/12134687/how-to-add-element-into-arraylist-in-hashmap
                // found the above usefull when ran into a problem
                List<Message>alist = message_list.get(message.sender);
                List<Message>blist = message_list.get(message.sender);
                if (alist == null)
                    alist = new ArrayList<>();
                if(blist == null)
                    blist = new ArrayList<>();
                alist.add(message);
                blist.add(message);
                return true;
            }
            return false;
        }                            
         return false; 
    }
    
    public ArrayList<Message> getMessages(Account a, Account b){
        // this gathers all of the messages between person a and b
        ArrayList<Message> mList = new ArrayList<>();
        for(Message m : message_list.get(a)){   // looping though person a's messages
            if(m.sender == b)       // seeing if the sender or recipient of the message was from person b
                mList.add(m);
            else if(m.recipient == b)
                mList.add(m);
        }
        sortMessages(mList,0,mList.size()-1);   // sort based on time incase it got messed up
        return mList;   // make sure that its sorting
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
    