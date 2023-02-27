package com.TwitterClone.ProjectBackend.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stores all tweets that contain this hashtag
 */
@Getter
@Setter
public class Hashtag {

    @Id
    private String name;

    @OneToMany
    private List<Tweet> tweetList;

    public Hashtag(String name) {
        this.name = name;
        this.tweetList = new ArrayList<>();
    }
}
