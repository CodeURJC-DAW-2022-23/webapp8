package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

/**
 * This Repository is the connection to DB for Tweet Entity
 */
@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    /**
     * This Query returns a List of Tweets written by a user ordered by the date when they were published
     * @param id
     * @return
     */
    @Query("SELECT t from Tweet t WHERE t.user.id = :id ORDER BY t.publishDate DESC")
    List<Tweet> findByUser(Long id);

    /**
     * This Query returns a List of Tweets written by the users who the user follows ordered by the date when they were published
     * @param id
     * @return
     */
    @Query(value = "SELECT * from tweet t join (SELECT followed_id from users_followed WHERE user_id = ?1) f on t.user_id = f.followed_id ORDER BY t.publish_date DESC LIMIT ?2, ?3", nativeQuery = true)
    List<Tweet> findByUserFollows(Long id, int init, int size);

    /**
     * This Query returns a List of Tweets bookmarked by the user
     * @param id
     * @param offset
     * @param size
     * @return
     */
    @Query(value = "SELECT * FROM tweet t JOIN (SELECT bookmarks_id FROM users_bookmarks WHERE user_id = ?1) b ON t.id = b.bookmarks_id ORDER BY publish_date LIMIT ?2,?3",nativeQuery = true)
    List<Tweet>findBookmarksByUserId(Long id, int offset, int size);

    /**
     * This Query returns a List of Tweets that are comments of another Tweet
     * @param id
     * @param offset
     * @param size
     * @return
     */
    @Query(value ="SELECT * from  tweet_comments WHERE tweet_id = ?1 ORDER BY publish_date LIMIT ?2,?3", nativeQuery = true)
    List<Tweet> findCommentsById(Long id, int offset, int size);

    /**
     * This Query returns a List of Tweets retweeted by the user
     * @param id
     * @param offset
     * @param size
     * @return
     */
    @Query(value ="SELECT * FROM tweet_retweets NATURAL JOIN tweet WHERE retweets_id = ?1 ORDER BY publish_date DESC LIMIT ?2,?3", nativeQuery = true)
    List<Tweet> findRetweetsByUser(Long id, int offset, int size);

    /**
     * This Query returns a List of Tweets liked by the user
     * @param id
     * @param offset
     * @param size
     * @return
     */
    @Query(value ="SELECT * FROM tweet_likes NATURAL JOIN tweet WHERE like_id = ?1 ORDER BY publish_date DESC LIMIT ?2,?3", nativeQuery = true)
    List<Tweet> findLikesByUser(Long id, int offset, int size);

    /**
     * This Query returns the amount of likes a Tweet has
     * @param id
     * @return
     */
    @Query(value="SELECT COUNT(*) FROM tweet_likes JOIN tweet ON tweet_id = id WHERE tweet_id = ?1 GROUP BY tweet_id", nativeQuery = true)
    Long countLikes(Long id);

    /**
     * This Query returns the amount of retweets a Tweet has
     * @param id
     * @return
     */
    @Query(value="SELECT COUNT(*) FROM tweet_retweets JOIN tweet ON tweet_id = id WHERE tweet_id = ?1 GROUP BY tweet_id", nativeQuery = true)
    Long countRetweets(Long id);

    /**
     * This Query returns the amount of comments a Tweet has
     * @param id
     * @return
     */
    @Query(value="SELECT COUNT(*) FROM tweet_comments JOIN tweet ON tweet_id = id WHERE tweet_id = ?1 GROUP BY tweet_id", nativeQuery = true)
    Long countComments(Long id);
    @Query(value = "SELECT tweet.* FROM hashtag_tweets JOIN tweet ON tweets_id = id WHERE hashtag_hashtag = ?1", nativeQuery = true)
    List<Tweet> getTweetsOfTrend(String id);
}
