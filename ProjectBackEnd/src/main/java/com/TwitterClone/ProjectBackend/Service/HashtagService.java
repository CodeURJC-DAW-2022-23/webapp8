package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Hashtag;
import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.HashtagRepository;
import com.TwitterClone.ProjectBackend.Repository.TweetRepository;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class HashtagService {

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    public List<Trend> getCurrentTrends(int init, int size) {
        List<Tuple> trends= this.hashtagRepository.find(init, size);
        List<Tuple> trendsList = trends.stream().toList();
        return this.converterToTrend(trendsList);
    }
    public void add(String hashtag, Tweet tweet){
        Optional<Hashtag> h = this.hashtagRepository.findById(hashtag);
        if (h.isPresent()){
            Hashtag h2 = h.get();
            h2.addTweet(tweet);
            this.hashtagRepository.save(h2);
        } else {
            Set<Tweet> set = new HashSet<>();
            set.add(tweet);
            Hashtag h2 = new Hashtag(hashtag, set);
            this.hashtagRepository.save(h2);
        }
    }

    private List<Trend> converterToTrend(List<Tuple> trends){
        List<Trend> list = new LinkedList<>();

        for (int i = 0; i < trends.size(); i++){
            String numTweets = trends.get(i).get("numtweets").toString();
            String hashtag = trends.get(i).get("hashtag").toString();
            list.add(new Trend(hashtag,Integer.parseInt(numTweets)));
        }

        return list;
    }

    public List<Tweet> getTweetsAssociatedTo(String hashtag, int from, int size) {
        return this.tweetRepository.getTweetsOfTrend(hashtag, from, size);
    }

    public int countTrends() {
        return this.hashtagRepository.countTrends();
    }

    public int countTweetsAssociated(String hashtag) {
        return this.hashtagRepository.countTweetsAssociated(hashtag);
    }
}
