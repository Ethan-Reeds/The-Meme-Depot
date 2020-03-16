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
public class PostManager {
    protected static Map<String, Post> postList = new HashMap<String, Post>();
    
    public static boolean post(String text){
        if(postList.isEmpty()){
            return false;
        }
        return (postList.get(text).getTextPost().equals(text));
    }
    
    public static boolean addPost( String post){
        if(post.length() != 1){
            return false;
        }
        if(!postList.isEmpty()){
            return false;
        }
        postList.put(post, new Post(post));
        return true;
    }
}
