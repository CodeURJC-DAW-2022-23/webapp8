package com.TwitterClone.ProjectBackend.Service;

import com.TwitterClone.ProjectBackend.Model.Hashtag;
import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.HashtagRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class HashtagService {

    @Autowired
    private HashtagRepository trendRepository;

    public List<Trend> getCurrentTrends(int init, int size) {
        List<Tuple> trends= this.trendRepository.find(init, size);
        List<Tuple> trendsList = trends.stream().toList();
        return this.converterToTrend(trendsList);
    }
    public void add(String hashtag, Tweet tweet){
        Optional<Hashtag> h = this.trendRepository.findById(hashtag);
        if (h.isPresent()){
            Hashtag h2 = h.get();
            h2.addTweet(tweet);
            this.trendRepository.save(h2);
        } else {
            Set<Tweet> set = new HashSet<>();
            set.add(tweet);
            Hashtag h2 = new Hashtag(hashtag, set);
            this.trendRepository.save(h2);
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
}
