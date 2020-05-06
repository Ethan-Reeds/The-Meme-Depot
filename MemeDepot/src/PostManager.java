/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hannah Hawkins
 */

import java.util.*;
import java.sql.*;

public class PostManager {
    
    static PostManager instance = new PostManager();
    
    public PostManager() {}
    
    public Post getPost(SQLSearch search) {
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            String querystring = "SELECT * FROM posts WHERE " + search.query;
            var result_list = new ArrayList<Post>();
            for(var item : ParameterizedStatement.executeOneQuery(
                    conn, querystring, search.params)) {
                result_list.add(Post.fromSQL(item));
            }
            if(result_list.size() > 0) {
                return result_list.get(0);
            }
            else {
                return null;
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Tag getTag(SQLSearch search) {
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            String querystring = "SELECT * FROM tag_info WHERE " + search.query;
            var result_list = new ArrayList<Tag>();
            for(var item : ParameterizedStatement.executeOneQuery(
                    conn, querystring, search.params)) {
                result_list.add(Tag.fromSQL(item));
            }
            if(result_list.size() > 0) {
                return result_list.get(0);
            }
            else {
                return null;
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static boolean createPost(Account acc, byte[] img) {
        // postID is auto-incremented
        // likes is 0 by default
        // delete_date is null by default
        
        if(acc == null || img == null) {
            // missing parameter(s)
            return false;
        }
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            var msecs = System.currentTimeMillis();
            String date = new java.sql.Date(msecs).toString();
            // add to database
            ParameterizedStatement.executeOneUpdate(conn, 
                    "INSERT INTO posts(userID, image, date) VALUES(?,?,?);",
                    acc.getUserID(), img, date);
            return true;
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void deletePost(Post post) {
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            ParameterizedStatement.executeOneUpdate(conn,
                    // delete tags on post
                    "DELETE FROM post_tags WHERE postID=?;",
                    post.getPostID()
            );
            ParameterizedStatement.executeOneUpdate(conn,
                    // delete post itself
                    "DELETE FROM posts WHERE postID=?;",
                    post.getPostID()
            );
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static boolean addTagToPost(String text, Post post) {
        if(text.isEmpty() || post == null) {
            // missing parameters
            return false;
        }
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            var tag_query = PostManager.instance.getTag(new SQLSearch(
                        "text=?;", new Object[]{text}
                ));
            if(tag_query == null) {
                // tag doesn't exist yet - create it
                ParameterizedStatement.executeOneUpdate(conn,
                        "INSERT INTO tag_info(text) VALUES(?);", text);
                // reassign tag_query to new tag
                tag_query = PostManager.instance.getTag(new SQLSearch(
                        "text=?;", new Object[]{text}
                ));
            }
            ParameterizedStatement.executeOneUpdate(conn,
                    "INSERT INTO post_tags(postID, tagID) VALUES(?,?);",
                    post.getPostID(), tag_query.getTagID());
            return true;
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static boolean removeTagFromPost(Tag tag, Post post) {
        if(tag == null || post == null) {
            // missing parameters
            return false;
        }
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            var tag_query = ParameterizedStatement.executeOneQuery(conn, 
                    "SELECT * FROM post_tags WHERE postID=? AND tagID=?;",
                    post.getPostID(), tag.getTagID());
            if(tag_query == null) {
                // tag is not linked to post
                return false;
            }
            else {
                ParameterizedStatement.executeOneUpdate(conn,
                        "DELETE FROM post_tags WHERE postID=? AND tagID=?;",
                        post.getPostID(), tag.getTagID());
                return true;
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void clearPosts() {
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            ParameterizedStatement.executeOneUpdate(conn,
                    // deletes everything from posts table
                    // all postIDs should be greater than -1
                    "DELETE FROM posts WHERE postID>?;", -1);
            ParameterizedStatement.executeOneUpdate(conn,
                    // deletes everything from post_tags table
                    // all postIDs should be greater than -1
                    "DELETE FROM post_tags WHERE postID>?;", -1);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void clearTags() {
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            ParameterizedStatement.executeOneUpdate(conn,
                    // deletes everything from tag_info table
                    // all tagIDs should be greater than -1
                    "DELETE FROM tag_info WHERE tagID>?;", -1);
            ParameterizedStatement.executeOneUpdate(conn,
                    // deletes everything from post_tags table
                    // all tagIDs should be greater than -1
                    "DELETE FROM post_tags WHERE tagID>?;", -1);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}