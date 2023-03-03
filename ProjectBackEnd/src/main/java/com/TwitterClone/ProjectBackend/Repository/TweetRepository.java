package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);
    @Query("SELECT t from Tweet t WHERE t.user.id = :id ORDER BY t.publishDate DESC")
    List<Tweet> findByUser(Long id);

    @Query(value = "SELECT * from tweet t join (SELECT followed_id from users_followed WHERE user_id = ?1) f on t.user_id = f.followed_id ORDER BY t.publish_date DESC", nativeQuery = true)
    List<Tweet> findByUserFollows(Long id);

    @Query(value = "SELECT * FROM tweet t JOIN (SELECT bookmarks_id FROM users_bookmarks WHERE user_id = ?1) b ON t.id = b.bookmarks_id",nativeQuery = true)
    List<Tweet>findBookmarksByUserId(Long id);

    @Query(value ="SELECT * from  tweet_comments WHERE tweet_id = ?1 ORDER BY publish_date LIMIT ?2,?3", nativeQuery = true)
    List<Tweet> findCommentsById(Long id, int offset, int size);

    @Query(value ="SELECT * FROM tweet_retweets NATURAL JOIN tweet WHERE retweets_id = ?1 ORDER BY publish_date DESC LIMIT ?2,?3", nativeQuery = true)
    List<Tweet> findRetweetsByUser(Long id, int offset, int size);

    @Query(value ="SELECT * FROM tweet_likes NATURAL JOIN tweet WHERE like_id = ?1 ORDER BY publish_date DESC LIMIT ?2,?3", nativeQuery = true)
    List<Tweet> findLikesByUser(Long id, int offset, int size);

    @Query(value="SELECT COUNT(*) FROM tweet_likes JOIN tweet ON tweet_id = id WHERE tweet_id = ?1 GROUP BY tweet_id", nativeQuery = true)
    Long countLikes(Long id);
    @Query(value="SELECT COUNT(*) FROM tweet_retweets JOIN tweet ON tweet_id = id WHERE tweet_id = ?1 GROUP BY tweet_id", nativeQuery = true)
    Long countRetweets(Long id);
    @Query(value="SELECT COUNT(*) FROM tweet_comments JOIN tweet ON tweet_id = id WHERE tweet_id = ?1 GROUP BY tweet_id", nativeQuery = true)
    Long countComments(Long id);

    /*@Query(value="SELECT \"media1\" FROM \"tweet\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findMedia1ByUserId(Long id);
    @Query(value="SELECT \"media2\" FROM \"tweet\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findMedia2ByUserId(Long id);
    @Query(value="SELECT \"media3\" FROM \"tweet\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findMedia3ByUserId(Long id);
    @Query(value="SELECT \"media4\" FROM \"tweet\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findMedia4ByUserId(Long id);*/
}
