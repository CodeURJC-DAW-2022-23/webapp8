package com.TwitterClone.ProjectBackend.Model.MustacheObjects;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class to store all the information of a tweet to be show at mustache
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TweetInformation {
    private Tweet tweet;
    private Long numComments;
    private Long numRetweets;
    private Long numLikes;
    private String colorLike;
    private String colorRetweet;
    private String colorBookmark;
    private boolean isAuthorised;
}
