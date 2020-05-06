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

public class Tag {
    protected int tagID;    // auto-increments in database
    protected String text;
    
    public Tag(String text) {
        this.text = text;
    }
    
    public static Tag fromSQL(Map<String, ParameterizedStatement.Value> M) {
        return new Tag(M.get("text").asString());
    }
    
    public int getTagID() {
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            var query = ParameterizedStatement.executeOneQuery(conn, 
                    "SELECT tagID FROM tag_info WHERE text=?;", 
                    this.text).getAll();
            // sets postID to auto-incremented int from database
            // get the value of key "tagID" in 1st result of query as an int
            this.tagID = query.get(0).get("tagID").asInt();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
        return this.tagID;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(String newText) {
        this.text = newText;
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            // update text in database
            ParameterizedStatement.executeOneUpdate(conn,
                    "UPDATE tag_info SET text=? WHERE tagID=?;",
                    newText, this.tagID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
