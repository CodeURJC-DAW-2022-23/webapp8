package com.TwitterClone.ProjectBackend.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the access to search system and the trends
 */
public class Explorer {
    private List<Trend> trends;

    public Explorer() {
        this.trends = new ArrayList<>();
        Hashtag hashtag = new Hashtag("RLCS");
        Trend trend1 = new Trend("North America", hashtag , 20);
        trends.add(trend1);
        Trend trend2 = new Trend(null, hashtag, 521);
        trends.add(trend2);
        Trend trend3 = new Trend(null, hashtag, 7987);
        trends.add(trend3);
        Trend trend4 = new Trend("Spain", hashtag, 1234);
        trends.add(trend4);
    }

    /**
     * Prepares the object to be used by templates
     */
    public void showInformation(){

    }

    /**
     * Obtains hashtags from HashtagController or SearchController
     */
    private void askHashtags(){

    }

    /**
     * Return a list with the current trends
     * @return
     */
    public List<Trend> getTrends() {
        return this.trends;
    }
}
