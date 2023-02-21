package View;

import Model.*;

import java.io.File;
import java.util.ArrayList;

/**
 * This class is on charge of showing all the elements of a tweet to the user
 */
public class TweetView {

    private Like likeBottom;
    private Retweet retweetBottom;
    private ArrayList<Comment> comments;
    private Bookmark bookmark;
    private String text;
    private File[] media;

    /**
     * This method shows all the information OF A SINGLE tweet
     * @param tweet
     */
    public void visualizeTweet(Tweet tweet){

    }

    /**
     * This method loads all the information of a tweet, so it can be shown
     * @param tweet
     */
    public void loadTweetInformation(Tweet tweet){

    }

}
