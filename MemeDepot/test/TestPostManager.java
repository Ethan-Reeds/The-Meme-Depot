/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hannah Hawkins
 */

import static org.testng.Assert.*;
import org.testng.annotations.*;

public class TestPostManager {
    
    public TestPostManager() {}
    
    // ::: GET POST ::: //
    
    @Test
    public void testGetPost() {
        // basic test for getPost()
        PostManager.clearPosts();
        AccountManager.clear();
        AccountManager.addUser("Username123", "Password123", "email@gmail.com", 
                "1999", "12", "02");
        Account acc = AccountManager.instance.getAccount(new SQLSearch(
                "username=?", new Object[]{"Username123"})
        );
        byte[] img = "image".getBytes();
        PostManager.createPost(acc, img);
        Post post = PostManager.instance.getPost(new SQLSearch(
                "userID=?", new Object[]{acc.getUserID()})
        );
        assertTrue(post != null && post.getUserID() == acc.getUserID());
    }
    
    @Test
    public void testGetPostDoesntExist() {
        // tests that method returns null when post doesn't exist
        PostManager.clearPosts();
        AccountManager.clear();
        AccountManager.addUser("Username123", "Password123", "email@gmail.com", 
                "1999", "12", "02");
        Account acc = AccountManager.instance.getAccount(new SQLSearch(
                "username=?", new Object[]{"Username123"})
        );
        Post post = PostManager.instance.getPost(new SQLSearch(
                "userID=?", new Object[]{acc.getUserID()})
        );
        assertTrue(post == null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetPostBadAccountSearch() {
        // tests that method errors when given bad account search query
        PostManager.clearPosts();
        AccountManager.clear();
        AccountManager.addUser("Username123", "Password123", "email@gmail.com", 
                "1999", "12", "02");
        Account acc = AccountManager.instance.getAccount(new SQLSearch(
                "username=?", new Object[]{"wrong"})
        );
        byte[] img = "image".getBytes();
        PostManager.createPost(acc, img);
        PostManager.instance.getPost(new SQLSearch(
                "userID=?", new Object[]{acc.getUserID()})
        );
    }
    
    @Test
    public void testGetPostBadPostSearch() {
        // tests that method returns null when given bad post search query
        PostManager.clearPosts();
        AccountManager.clear();
        AccountManager.addUser("Username123", "Password123", "email@gmail.com", 
                "1999", "12", "02");
        Account acc = AccountManager.instance.getAccount(new SQLSearch(
                "username=?", new Object[]{"Username123"})
        );
        byte[] img = "image".getBytes();
        PostManager.createPost(acc, img);
        Post post = PostManager.instance.getPost(new SQLSearch(
                "userID=?", new Object[]{"wrong"})
        );
        assertTrue(post == null);
    }
    
    
    
    // ::: GET TAG ::: //
    
    @Test
    public void testGetTag() {
        // basic test for getTag()
        PostManager.clearPosts();
        AccountManager.clear();
        AccountManager.addUser("Username123", "Password123", "email@gmail.com", 
                "1999", "12", "02");
        Account acc = AccountManager.instance.getAccount(new SQLSearch(
                "username=?", new Object[]{"Username123"})
        );
        byte[] img = "image".getBytes();
        PostManager.createPost(acc, img);
        Post post = PostManager.instance.getPost(new SQLSearch(
                "userID=?", new Object[]{acc.getUserID()})
        );
        PostManager.addTagToPost("text", post);
        Tag tag = PostManager.instance.getTag(new SQLSearch(
                "text=?", new Object[]{"text"})
        );
        assertTrue(tag.getText().equals("text"));
    }
    
    
    
    // ::: CREATE POST ::: //
    
    @Test
    public void testCreatePost() {
        // basic test for createPost()
        PostManager.clearPosts();
        AccountManager.clear();
        AccountManager.addUser("Username123", "Password123", "email@gmail.com", 
                "1999", "12", "02");
        Account acc = AccountManager.instance.getAccount(new SQLSearch(
                "username=?", new Object[]{"Username123"})
        );
        byte[] img = "image".getBytes();
        assertTrue(PostManager.createPost(acc, img));
    }
    
    @Test
    public void testCreatePostNoParameters() {
        // tests that you can't add user with no data
        assertFalse(PostManager.createPost(null, null));
    }
    
    @Test
    public void testCreatePostNoAccount() {
        // tests that you can't add user with no data
        byte[] img = {0,1,2,3,4,5};
        assertFalse(PostManager.createPost(null, img));
    }
    
    @Test
    public void testCreatePostNoImage() {
        // tests that you can't add user with no data
        PostManager.clearPosts();
        AccountManager.clear();
        AccountManager.addUser("Username123", "Password123", "email@gmail.com", 
                "1999", "12", "02");
        Account acc = AccountManager.instance.getAccount(new SQLSearch(
                "username=?", new Object[]{"Username123"})
        );
        assertFalse(PostManager.createPost(acc, null));
    }
    
    // ::: DELETE POST ::: //
    
    @Test
    public void testDeletePost() {
        // basic test for deletePost()
        PostManager.clearPosts();
        AccountManager.clear();
        AccountManager.addUser("Username123", "Password123", "email@gmail.com", 
                "1999", "12", "02");
        Account acc = AccountManager.instance.getAccount(new SQLSearch(
                "username=?", new Object[]{"Username123"})
        );
        byte[] img = "image".getBytes();
        PostManager.createPost(acc, img);
        PostManager.deletePost(
                PostManager.instance.getPost(new SQLSearch(
                        "userID=?", new Object[]{acc.getUserID()}
                )));
        // check that post doesn't exist anymore
        assertTrue(PostManager.instance.getPost(new SQLSearch(
                        "userID=?", new Object[]{acc.getUserID()}
                )) == null);
    }
}
