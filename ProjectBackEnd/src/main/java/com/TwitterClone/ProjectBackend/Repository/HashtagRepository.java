package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Hashtag;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

/**
 * This Repository is the connection to DB for Hashtag Entity
 */
@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, String> {
    /**
     * This Query returns the Hashtags and the number of Tweet that contains the Hashtag
     * @param offset
     * @param size
     * @return
     */
    @Query(value = "SELECT hashtag_hashtag AS hashtag, COUNT(*) numTweets FROM hashtag_tweets GROUP BY hashtag_hashtag ORDER BY numTweets DESC, hashtag LIMIT ?1,?2", nativeQuery = true)
    List<Tuple> find(int offset, int size);

    /**
     * This Query returns a List of Tweet that contains a specific Hashtag
     * @param id
     * @return
     */
    @Query(value = "SELECT tweet.* FROM hashtag_tweets JOIN tweet ON tweets_id = id WHERE hashtag_hashtag = ?1", nativeQuery = true)
    List<Tweet> getTweetsOfTrend(String id);

    /**
     * This Query returns the number of rows of the repository
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM hashtag", nativeQuery = true)
    int countTrends();

    @Query(value = "SELECT count(*) FROM hashtag_tweets WHERE hashtag_hashtag = ?1", nativeQuery = true)
    int countTweetsAssociated(String hashtag);
    List<Hashtag> findByHashtagIsContainingIgnoreCase(String name);

}
