package com.TwitterClone.ProjectBackend.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trend {

    @Id
    private String hashtag;
    @NonNull
    private int numTweets;

}
