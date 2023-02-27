package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);
    @Query("select t from Tweet t where t.user.id = :id order by t.publishDate desc")
    List<Tweet> findByUser(Long id);

    @Query(value = "select * from \"tweet\" t join (select \"followed_id\" from \"users_followed\" where \"user_id\" = ?1) f on t.\"user_id\" = f.\"followed_id\" order by t.\"publish_date\" desc limit ?2", nativeQuery = true)
    List<Tweet> findByUserFollows(Long id, int amount);

    @Query(value ="select * from  \"tweet_comments\" where \"tweet_id\" = ?1 order by \"publish_date\" desc limit ?2;", nativeQuery = true)
    List<Tweet> findCommentsById(Long id, int amount);

    @Query(value ="SELECT * FROM \"tweet_retweets\" NATURAL JOIN \"tweet\" where \"retweets_id\" = ?1 order by \"publish_date\" desc limit ?2;", nativeQuery = true)
    List<Tweet> findRetweetsByUser(Long id, int amount);

    @Query(value ="SELECT * FROM \"tweet_likes\" NATURAL JOIN \"tweet\" where \"like_id\" = ?1 order by \"publish_date\" desc limit ?2;", nativeQuery = true)
    List<Tweet> findLikesByUser(Long id, int amount);

}
