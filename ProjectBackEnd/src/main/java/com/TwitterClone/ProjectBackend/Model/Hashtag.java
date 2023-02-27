package com.TwitterClone.ProjectBackend.Model;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hashtag {

    @Id
    private String hashtag;
    @ManyToMany
    private Set<Tweet> tweets = new HashSet<>();

    public void addTweet(Tweet tweet) {
        this.tweets.add(tweet);
    }
}
