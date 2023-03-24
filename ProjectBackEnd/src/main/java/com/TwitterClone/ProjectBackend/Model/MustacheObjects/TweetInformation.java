package com.TwitterClone.ProjectBackend.Model.MustacheObjects;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.fasterxml.jackson.annotation.JsonView;
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
    public interface Basic {
    }

    @JsonView(Basic.class)
    private Tweet tweet;
    @JsonView(Basic.class)
    private Long numComments;
    @JsonView(Basic.class)
    private Long numRetweets;
    @JsonView(Basic.class)
    private Long numLikes;
    @JsonView(Basic.class)
    private String colorLike;
    @JsonView(Basic.class)
    private String colorRetweet;
    @JsonView(Basic.class)
    private String colorBookmark;
    @JsonView(Basic.class)
    private boolean isAuthorised;
}
