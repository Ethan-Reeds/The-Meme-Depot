/**
 *
 * @author cayle
 */

public class Post{
    public byte[] pic;          //Picture sender is posting
    public String text_post;    //Message in words
    public Account poster;      //Who sent the post
    
    public Post(String text){
        text_post = text;
        pic = null;
    }
    
    public String getTextPost(){
        return text_post;
    }
    
    public Account getPoster(){
        return poster;
    }
    
    public byte[] getPic(){
        return pic;
    }
}
