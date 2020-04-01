/**
 *
 * @author cayle
 */

public class Comment{
    public byte[] pic;          //Picture sender is posting
    public String text_post;    //Message in words
    public Account commenter;      //Who sent the post
    
    public Comment(String text){
        text_post = text;
        pic = null;
    }
    
    public String getCommentPost(){
        return text_post;
    }
    
    public Account getCommenter(){
        return commenter;
    }
    
}
