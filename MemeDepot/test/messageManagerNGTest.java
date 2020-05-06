/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author user
 */
public class messageManagerNGTest {
    
    public messageManagerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        messageManager.clearList();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of addMessage method, of class messageManager.
     */
    @Test
    public void testAddMessage_string_messege() {
        System.out.println("addMessage_string");
        messageManager mInstance = new messageManager();
        Account sender = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20");
        Account reciever = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35");
        AccountManager.addUser(sender.username,sender.password,sender.email,"220","04","20");
        AccountManager.addUser(reciever.username,sender.password,reciever.email,"2343","02","35");
        Message message = new Message("hey do you want to be on 30 rock? lol", sender, reciever);
        boolean expResult = true;
        boolean result = mInstance.addMessage(message);
        assertEquals(result, expResult);
    }
    @Test
    public void testAddMessage_img_messege() {
        System.out.println("addMessage_img");
        messageManager mInstance = new messageManager();
        Account sender = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20");
        Account reciever = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35");
        AccountManager.addUser(sender.username,sender.password,sender.email,"220","04","20");
        AccountManager.addUser(reciever.username,sender.password,reciever.email,"2343","02","35");
        Message message = new Message("hey do you want to be on 30 rock? lol", sender, reciever);
        boolean expResult = true;
        boolean result = mInstance.addMessage(message);
        assertEquals(result, expResult);
    }
    
    @Test
    public void testGetMessages_text(){
        System.out.println("getMessages");
        messageManager mInstance = new messageManager();
        // make accounts
        Account sender = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20");
        Account reciever = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35");
        AccountManager.addUser(sender.username,sender.password,sender.email,"220","04","20");
        AccountManager.addUser(reciever.username,sender.password,reciever.email,"2343","02","35");
        //make messeges
        Message message = new Message("hey do you want to be on 30 rock? lol", sender, reciever);
        for(int i=0;i<1000;i++)
        {;} // to make a time difference 
        Message message2 = new Message("nah lol i work at whalburgers now", reciever, sender);
        mInstance.addMessage(message);
        mInstance.addMessage(message2);
        //make the test
        ArrayList<Message> expResult = new ArrayList<>();
        expResult.add(message); expResult.add(message2);
        ArrayList<Message> result = mInstance.getMessages(sender,reciever);
        assertEquals(result, expResult);
    }
    
    @Test
    public void testGetMessages_img(){
        System.out.println("getMessages");
        messageManager mInstance = new messageManager();
        // make accounts
        Account sender = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20");
        Account reciever = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35");
        AccountManager.addUser(sender.username,sender.password,sender.email,"220","04","20");
        AccountManager.addUser(reciever.username,sender.password,reciever.email,"2343","02","35");
        //make messeges for byte arrays
        byte[] img1 = {4,4,6,3,4,9,7,6,1,9,3};
        byte[] img2 = {2,5,7,5,4,2,5,8};
        Message message = new Message(img1, sender, reciever);
        for(int i=0;i<1000;i++)
        {;} // to make a time difference 
        Message message2 = new Message(img2, reciever, sender);
        mInstance.addMessage(message);
        mInstance.addMessage(message2);
        //make the test
        ArrayList<Message> expResult = new ArrayList<>();
        expResult.add(message); expResult.add(message2);
        ArrayList<Message> result = mInstance.getMessages(sender,reciever);
        assertEquals(result, expResult);
    }
       @Test
        public void testGetMessages_img_text(){
        System.out.println("getMessages");
        messageManager mInstance = new messageManager();
        // make accounts
        Account sender = new Account("alecBaldwin@imGreat.com", "30Rock!","alecBaldwin@imGreat.com","220","04","20");
        Account reciever = new Account("markyMark@funkyBunch.com", "imAnActorN0w", "markyMark@funkyBunch.com","2343","02","35");
        AccountManager.addUser(sender.username,sender.password,sender.email,"220","04","20");
        AccountManager.addUser(reciever.username,sender.password,reciever.email,"2343","02","35");
        //make messeges for byte arrays
        byte[] img1 = {4,4,6,3,4,9,7,6,1,9,3};
        Message message = new Message(img1, sender, reciever);
        for(int i=0;i<1000;i++)
        {;} // to make a time difference 
        Message message2 = new Message("lmao funny pick bro", reciever, sender);
        mInstance.addMessage(message);
        mInstance.addMessage(message2);
        //make the test
        ArrayList<Message> expResult = new ArrayList<>();
        expResult.add(message); expResult.add(message2);
        ArrayList<Message> result = mInstance.getMessages(sender,reciever);
        assertEquals(result, expResult);
    }
}
