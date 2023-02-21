package Controller;

import Model.Tweet;
import Services.TweetService;

/**
 * This class is on charge of controlling all the events realted to the tweets. It takes the request from the view part
 * and send then to the model part (TweetsService) to gather the required data
 */
public class TweetController {
    private TweetService tweetService;

    /**
     * This method manage the publishing petition from the view
     * @param tweet
     */
    public void publish (Tweet tweet){

    }

    /**
     * This method manage the action of pressing the like button.
     * @param tweet
     */
    public void pressLike (Tweet tweet){

    }

    /**
     * This method manage the action of pressing the retweet button
     * @param tweet
     */
    public void pressRetweet (Tweet tweet){

    }

    /**
     * This method manage the action of pressing the bookmark button
     * @param tweet
     */
    public void pressBookmark (Tweet tweet){

    }

    /**
     * This method manage the action of adding a new comment to a tweet
     * @param tweet
     */
    public void addNewComment (Tweet tweet){

    }

    /**
     * This method manage the action of deleting a comment from a tweet
     * @param tweet
     */
    public void deleteComment (Tweet tweet){
        // Maybe this should be a responsibility for the tweet itself?
    }

}
