package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Hashtag;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, String> {
    @Query(value = "SELECT hashtag_hashtag AS hashtag, COUNT(*) numTweets FROM hashtag_tweets GROUP BY hashtag_hashtag ORDER BY numTweets DESC, hashtag LIMIT ?1,?2", nativeQuery = true)
    List<Tuple> find(int offset, int size);

    @Query(value = "SELECT * FROM hashtag_tweets WHERE hashtag_hashtag = ?1", nativeQuery = true)
    List<Tweet> getTweetsOfTrend(String id);
}
