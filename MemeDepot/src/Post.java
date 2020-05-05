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
                    "SELECT postID FROM posts WHERE userID=?;", 
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
    
    public void setImage(byte[] newImage) {
        this.image = newImage;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            // update image in database
            ParameterizedStatement.executeOneUpdate(conn,
                    "UPDATE posts SET image=? WHERE postID=?",
                    newImage, this.postID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public int getLikes() {
        return this.likes;
    }
    
    public void addLike() {
        this.likes += 1;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            // update likes in database
            ParameterizedStatement.executeOneUpdate(conn,
                    "UPDATE posts SET likes=? WHERE postID=?",
                    this.likes, this.postID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void removeLike() {
        this.likes -= 1;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            // update likes in database
            ParameterizedStatement.executeOneUpdate(conn,
                    "UPDATE posts SET likes=? WHERE postID=?",
                    this.likes, this.postID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public String getPostDate() {
        return this.post_date;
    }
    
    public String getDeleteDate() {
        return this.delete_date;
    }
    
    public void setDeleteDate(String date) {
        this.delete_date = date;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            // update delete date in database
            ParameterizedStatement.executeOneUpdate(conn,
                    "UPDATE posts SET delete_date=? WHERE postID=?",
                    this.delete_date, this.postID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
