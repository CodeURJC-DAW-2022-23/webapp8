package com.TwitterClone.ProjectBackend.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trend {
    private String countryTrend;
    private Hashtag hashtag;
    private int numTweets;

    public Trend(String countryTrend, Hashtag hashtag) {
        this.countryTrend = countryTrend;
        this.hashtag = hashtag;
        this.numTweets = hashtag.getTweetList().size();
    }
}
