package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stores all tweets that contain this hashtag
 */
public class Hashtag {

    private String name;

    private List<UUID> tweetList;

    public Hashtag(String name) {
        this.name = name;
        this.tweetList = new ArrayList<>();
    }
}
