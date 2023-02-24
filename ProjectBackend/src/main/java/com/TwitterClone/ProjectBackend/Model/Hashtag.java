package com.TwitterClone.ProjectBackend.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stores all tweets that contain this hashtag
 */
@Getter
@Setter
public class Hashtag {

    private String name;

    private List<Long> tweetList;

    public Hashtag(String name) {
        this.name = name;
        this.tweetList = new ArrayList<>();
    }
}
