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
        
        try(var conn = java.sql.DriverManager.getConnection(
                SQLAdminInfo.url, SQLAdminInfo.user, SQLAdminInfo.password)) {
            var update = ParameterizedStatement.executeOneUpdate(conn, 
                    "SELECT tagID FROM tag_info WHERE text=?;", 
                    this.text);
            // sets this.tagID to auto-incremented int from database
            this.tagID = update;
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static Tag fromSQL(Map<String, ParameterizedStatement.Value> M) {
        return new Tag(M.get("text").asString());
    }
    
    public int getTagID() {
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
                    "UPDATE tag_info SET text=? WHERE tagID=?",
                    newText, this.tagID);
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
