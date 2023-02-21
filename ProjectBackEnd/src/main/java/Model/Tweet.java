package Model;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 */

public class Tweet {

    private final UUID id;
    private User user; //Check if it's better with springSec Framework.
    private final LocalDate publishDate;
    private ArrayList<Like> likes;
    private ArrayList<Retweet> retweets;
    private ArrayList<Comment> comments;
    private String text;
    private File[] media;
    private String hashtag; // Later

    /**
     * At the beginning, a Tweet has no comments, likes or retweets, but it may contain media, text or hashtags selected
     * by the user when creating it.
     * The id is randomly selected and the date is the exact date in which it was published.
     */
    public Tweet() {
        this.id = UUID.randomUUID();
        this.publishDate = java.time.LocalDate.now();
        this.likes = new ArrayList<Like>();
        this.retweets =  new ArrayList<Retweet>();
        this.comments = new ArrayList<Comment>();
        this.text = addText();
        this.media= addMedia();
    }

    /* This two methods may not be needed.*/
    private File[] addMedia() {
        return null; //Return the set of media that the user wants to upload
    }

    public String addText(){
        return "";// Return the text written by the user
    }

}
