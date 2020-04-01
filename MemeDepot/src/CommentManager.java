/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.lang.String;
import java.util.function.BiConsumer;
/**
 *
 * @author cayle
 */
public class CommentManager {
    protected static Map<String, Comment> commentList = new HashMap<String, Comment>();
    
    public static boolean Comment(String text){
        if(commentList.isEmpty()){
            return false;
        }
        return (commentList.get(text).getCommentPost().equals(text));
    }
    
    public static boolean addComment( String post){
        if(post.length() != 1){
            return false;
        }
        if(!commentList.isEmpty()){
            return false;
        }
        commentList.put(post, new Comment(post));
        return true;
    }
}
