package Model;

public class Trend {
    private String countryTrend;
    private Hashtag hashtag;
    private int numTweets;



    public Trend(String countryTrend, Hashtag hashtag, int numTweets) {
        this.countryTrend = countryTrend;
        this.hashtag = hashtag;
        this.numTweets = numTweets;
    }
}
