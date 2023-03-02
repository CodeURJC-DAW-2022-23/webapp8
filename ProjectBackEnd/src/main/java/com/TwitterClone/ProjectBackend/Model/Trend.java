package com.TwitterClone.ProjectBackend.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Trend is a Model for showing data, but it is not an Entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trend {

    private String hashtag;
    private int numTweets;

}
