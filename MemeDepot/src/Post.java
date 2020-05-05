import java.util.*;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hannah Hawkins
 */
public class Post {
    protected int postID;           // auto-increments in database
    protected int userID;           // matches with a userID in users database
    protected byte[] image;
    protected int likes;
    protected String post_date;     
    protected String delete_date;   // default is NULL
    
    public Post(int userID, byte[] image) {
        this.userID = userID;
        this.image = image;
        this.likes = 0;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            var update = ParameterizedStatement.executeOneUpdate(conn, 
                    "SELECT postID FROM posts WHERE userID=?", 
                    this.userID);
            // sets postID to auto-incremented int from database
            this.postID = update;
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static Post fromSQL(Map<String, ParameterizedStatement.Value> M) {
        return new Post(
                M.get("userID").asInt(), 
                M.get("image").asBlob()
        );
    }
    
    public int getPostID() {
        return this.postID;
    }
    
    public int getUserID() {
        return this.userID;
    }
    
    public byte[] getImage() {
        return this.image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }
    
    public int getLikes() {
        return this.likes;
    }
    
    public void addLike() {
        this.likes += 1;
    }
    
    public void removeLike() {
        this.likes -= 1;
    }
    
    public String getPostDate() {
        return this.post_date;
    }
    
    public String getDeleteDate() {
        return this.delete_date;
    }
    
    public void setDeleteDate(String date) {
        this.delete_date = date;
    }
}
