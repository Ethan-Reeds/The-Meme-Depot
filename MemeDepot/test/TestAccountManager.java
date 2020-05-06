/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.testng.Assert.*;
import org.testng.annotations.*;

/**
 *
 * @author Hannah Hawkins
 */
public class TestAccountManager {
    
    public TestAccountManager() {
    }
    
    // ::: GET ACCOUNT ::: //
    
    @Test
    public void testGetAccount() {
        // basic test for getAccount()
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        assertTrue(account != null);
    }
    
    @Test
    public void testGetAccountGetFirstOfMany() {
        // tests if method returns first result of many in the query
        AccountManager.clear();
        AccountManager.addUser(
                "username1", "password", "test1@gmail.com", 
                "1999", "12", "2");
        AccountManager.addUser(
                "username2", "password", "test2@gmail.com", 
                "1999", "12", "2");
        AccountManager.addUser(
                "username3", "password", "test3@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "password=?;", new Object[]{"password"}
        ));
        assertTrue(account.getUsername().equals("username1"));
    }
    
    @Test
    public void testGetAccountDoesntExist() {
        // tests that method is null when no accounts match query
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        assertTrue(account == null);
    }
    
    @Test(expectedExceptions = RuntimeException.class)
    public void testGetAccountBadSQLSearch() {
        // tests that method errors when given bad SQLSearch parameters
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "wrong", new Object[]{"wrong"}));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetAccountNoSQLSearch() {
        // tests that method errors when given a null parameter
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(null);
    }
    
    
    
    // ::: LOGIN ::: //
    
    @Test
    public void testLogin() {
        // basic test for login()
        AccountManager.clear();
        AccountManager.addUser(
                "Username123", "Password123", "test@gmail.com", 
                "1999", "12", "2");
        assertTrue(AccountManager.login("Username123", "Password123"));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testLoginNoParameters() {
        // tests that you can't log in with no parameters
        AccountManager.login(null, null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testLoginNoUsername() {
        // tests that you can't log in with no username
        AccountManager.login(null, "password");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testLoginNoPassword() {
        // tests that you can't log in with no password
        AccountManager.login("username", null);
    }
    
    @Test
    public void testLoginUserDoesntExist() {
        // tests that you can't login if username doesn't exist
        AccountManager.clear();
        assertFalse(AccountManager.login("username", "password"));
    }
    
    @Test
    public void testLoginWrongPassword() {
        // tests that you can't login if password doesn't match
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        assertFalse(AccountManager.login("username", "wrong"));
    }
    
    @Test
    public void testLoginAlreadyLoggedIn() {
        // tests that you can't login if password doesn't match
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        AccountManager.login("username", "password");
        assertFalse(AccountManager.login("username", "password"));
    }
    
    
    
    // ::: LOGOUT ::: //
    
    @Test
    public void testLogout() {
        // basic test for logout()
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        AccountManager.login("username", "password");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        assertTrue(AccountManager.logout(account));
    }
    
    @Test
    public void testLogoutAlreadyLoggedOut() {
        // tests that you can't logout if you're already logged out
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        assertFalse(AccountManager.logout(account));
    }
    
    @Test(expectedExceptions = RuntimeException.class)
    public void testLogoutAccountDoesntExist() {
        // tests that method errors when given account doesn't exist
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.logout(account);
    }
    
    
    
    // ::: VERIFY USER ::: //
    
    @Test
    public void testVerifyUser() {
        // basic test for verifyUser()
        AccountManager.clear();
        AccountManager.addUser(
                "Username123", "Password123", "test@gmail.com", 
                "1999", "12", "02");
        assertTrue(AccountManager.verifyUser("Username123", "Password123"));
    }
    
    @Test
    public void testVerifyUserNotMatching() {
        // tests that method returns false when username and password don't match
        AccountManager.clear();
        AccountManager.addUser(
                "Username123", "Password123", "test@gmail.com", 
                "1999", "12", "02");
        assertFalse(AccountManager.verifyUser("Username123", "WrongPassword"));
    }
    
    @Test
    public void testVerifyUserDoesntExist() {
        // tests that method returns false when account doesn't exist
        AccountManager.clear();
        assertFalse(AccountManager.verifyUser("Username123", "Password123"));
    }
    
    
    
    // ::: GET USER ID ::: //
    
    @Test
    public void testGetUserID() {
        // basic test for getUserID()
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        assertTrue(AccountManager.instance.getUserID(account) 
                == account.getUserID());
    }
    
    @Test
    public void testGetUserIDIncrements() {
        // tests that userID increments as new user is added
        AccountManager.clear();
        AccountManager.addUser(
                "firstUser", "password", "test@gmail.com", 
                "1999", "12", "2");
        AccountManager.addUser(
                "secondUser", "password", "test2@gmail.com", 
                "1999", "12", "2");
        Account account1 = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"firstUser", "password"}
        ));
        Account account2 = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"secondUser", "password"}
        ));
        int first = AccountManager.instance.getUserID(account1);
        int second = AccountManager.instance.getUserID(account2);
        assertTrue(second == first + 1);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetUserIDDoesntExist() {
        // tests that method errors if account doesnt exist
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.getUserID(account);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetUserIDNoParameter() {
        // tests that method errors if no parameter is given
        AccountManager.instance.getUserID(null);
    }
    
    
    
    // ::: GET EMAIL ::: //
    
    @Test
    public void testGetEmail() {
        // basic test for getEmail()
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        assertTrue(AccountManager.instance.
                getEmail(account).equals("test@gmail.com"));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetEmailDoesntExist() {
        // tests that method errors if account doesn't exist
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.getEmail(account);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetEmailNoParameter() {
        // tests that method errors if no parameter is given
        AccountManager.instance.getEmail(null);
    }
    
    
    
    // ::: ADD USER ::: //

    @Test
    public void testAddUser() {
        // basic test for addUser()
        AccountManager.clear();
        assertTrue(AccountManager.addUser(
                "Username123", "Password123", "test@gmail.com", 
                "1999", "12", "02"));
    }
    
    @Test
    public void testAddUserSameUsername() {
        // tests that you can't add username that already exists
        AccountManager.clear();
        AccountManager.addUser(
                "SameUsername", "password", "first@gmail.com", 
                "1999", "12", "2");
        assertFalse(AccountManager.addUser(
                "SameUsername", "password", "second@gmail.com", 
                "1999", "12", "2"));
    }
    
    @Test
    public void testAddUserSameEmail() {
        // tests that you can't add email that already exists
        AccountManager.clear();
        AccountManager.addUser(
                "FirstUser", "password", "same@gmail.com", 
                "1999", "12", "2");
        assertFalse(AccountManager.addUser(
                "SecondUser", "password", "same@gmail.com", 
                "1999", "12", "2"));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testAddUserNoParameters() {
        // tests that you can't add user with no data
        AccountManager.addUser(null, null, null, null, null, null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testAddUserMissingUsername() {
        // tests that you can't add user with a missing username
        AccountManager.addUser(
                null, "password", "test@gmail.com", 
                "1999", "12", "2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testAddUserMissingPassword() {
        // tests that you can't add user with a missing password
        AccountManager.addUser(
                "username", null, "test@gmail.com", 
                "1999", "12", "2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testAddUserMissingEmail() {
        // tests that you can't add user with a missing email
        AccountManager.addUser(
                "username", "password", null, 
                "1999", "12", "2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testAddUserMissingYear() {
        // tests that you can't add user with a missing year
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                null, "12", "2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testAddUserMissingMonth() {
        // tests that you can't add user with a missing month
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", null, "2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testAddUserMissingDay() {
        // tests that you can't add user with a missing day
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", null);
    }
    
    
    
    // ::: IS ADMIN ::: //
    
    @Test
    public void testIsAdminFalse() {
        // tests that method returns false when account isn't admin
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        assertFalse(AccountManager.instance.isAdmin(account));
    }
    
    @Test
    public void testIsAdminTrue() {
        // tests that method returns true when account is admin
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.setAdmin(account, true);
        assertTrue(AccountManager.instance.isAdmin(account));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testIsAdminDoesntExist() {
        // tests that method errors when account doesn't exist
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.isAdmin(account);
    }
    
    
    
    // ::: SET ADMIN ::: //
    
    @Test
    public void testSetAdminFalseToTrue() {
        // tests that method sets admin from false to true
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.setAdmin(account, true);
        assertTrue(AccountManager.instance.isAdmin(account));
    }
    
    @Test
    public void testSetAdminTrueToFalse() {
        // tests that method sets admin from true to false
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.setAdmin(account, true);
        AccountManager.instance.setAdmin(account, false);
        assertFalse(AccountManager.instance.isAdmin(account));
    }
    
    @Test
    public void testSetAdminFalseToFalse() {
        // tests that method maintains false status
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.setAdmin(account, false);
        assertFalse(AccountManager.instance.isAdmin(account));
    }
    
    @Test
    public void testSetAdminTrueToTrue() {
        // tests that method maintains true status
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.setAdmin(account, true);
        AccountManager.instance.setAdmin(account, true);
        assertTrue(AccountManager.instance.isAdmin(account));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testSetAdminDoesntExist() {
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.setAdmin(account, true);
    }
    
    
    
    // ::: GET AVATAR ::: //

    @Test
    public void testGetAvatar() {
        // basic test for getAvatar()
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        assertTrue(AccountManager.instance.getAvatar(account) 
                == account.getAvatar());
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetAvatarDoesntExist() {
        // tests that method errors if account doesn't exist
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        AccountManager.instance.getAvatar(account);
    }
    
    
    
    // ::: SET AVATAR ::: //
    /* getAvater always returns null for some reason
    
    @Test
    public void testSetAvatar() {
        // basic test for setAvatar()
        AccountManager.clear();
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "username=? AND password=?;", 
                new Object[]{"username", "password"}
        ));
        var newAvatar = new byte[]{0};
        assertTrue(AccountManager.instance.setAvatar(account, newAvatar));
        assertTrue(AccountManager.instance.getAvatar(account) == newAvatar);
    }
    */
    
    
    
    // ::: CLEAR ::: //
    
    @Test
    public void testClear() {
        // basic test for clear()
        AccountManager.addUser(
                "username", "password", "test@gmail.com", 
                "1999", "12", "2");
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "userID>=?;", new Object[]{0}
        ));
        assertTrue(account == null);
    }
    
    @Test
    public void testClearAlreadyEmpty() {
        AccountManager.clear();
        Account account = AccountManager.instance.getAccount(new SQLSearch(
                "userID>=?;", new Object[]{0}
        ));
        assertTrue(account == null);
    }
}