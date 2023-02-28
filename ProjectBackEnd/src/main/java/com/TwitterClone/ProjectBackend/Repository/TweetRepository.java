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
    @Query("select t from Tweet t where t.user.id = :id order by t.publishDate desc")
    List<Tweet> findByUser(Long id);

    @Query(value = "select * from \"tweet\" t join (select \"followed_id\" from \"users_followed\" where \"user_id\" = ?1) f on t.\"user_id\" = f.\"followed_id\" order by t.\"publish_date\" desc limit ?2", nativeQuery = true)
    List<Tweet> findByUserFollows(Long id);

    @Query(value = "SELECT * FROM \"tweet\" t JOIN (SELECT \"bookmarks_id\" FROM \"users_bookmarks\" WHERE \"user_id\" = 1) b ON t.\"id\" = b.\"bookmarks_id\"",nativeQuery = true)
    List<Tweet>findBookmarksByUserId(Long id);

    @Query(value ="select * from  \"tweet_comments\" where \"tweet_id\" = ?1 order by \"publish_date\"", nativeQuery = true)
    Page<Tweet> findCommentsById(Long id, Pageable page);

    @Query(value ="SELECT * FROM \"tweet_retweets\" NATURAL JOIN \"tweet\" where \"retweets_id\" = ?1 order by \"publish_date\" desc limit ?2;", nativeQuery = true)
    Page<Tweet> findRetweetsByUser(Long id, Pageable page);

    @Query(value ="SELECT * FROM \"tweet_likes\" NATURAL JOIN \"tweet\" where \"like_id\" = ?1 order by \"publish_date\" desc limit ?2;", nativeQuery = true)
    Page<Tweet> findLikesByUser(Long id, Pageable page);

    @Query(value="SELECT COUNT(*) FROM \"tweet_likes\" JOIN \"tweet\" ON \"tweet_id\" = \"id\" WHERE \"tweet_id\" = ?1 GROUP BY \"tweet_id\"", nativeQuery = true)
    int countLikes(Long id);
    @Query(value="SELECT COUNT(*) FROM \"tweet_retweets\" JOIN \"tweet\" ON \"tweet_id\" = \"id\" WHERE \"tweet_id\" = ?1 GROUP BY \"tweet_id\"", nativeQuery = true)
    int countRetweets(Long id);
    @Query(value="SELECT COUNT(*) FROM \"tweet_comments\" JOIN \"tweet\" ON \"tweet_id\" = \"id\" WHERE \"tweet_id\" = ?1 GROUP BY \"tweet_id\"", nativeQuery = true)
    int countComments(Long id);

    /*@Query(value="SELECT \"media1\" FROM \"tweet\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findMedia1ByUserId(Long id);
    @Query(value="SELECT \"media2\" FROM \"tweet\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findMedia2ByUserId(Long id);
    @Query(value="SELECT \"media3\" FROM \"tweet\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findMedia3ByUserId(Long id);
    @Query(value="SELECT \"media4\" FROM \"tweet\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findMedia4ByUserId(Long id);*/
}
