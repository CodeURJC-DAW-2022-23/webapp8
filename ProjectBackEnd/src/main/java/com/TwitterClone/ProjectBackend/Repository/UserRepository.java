package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This Repository is the connection to DB for User Entity
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * This Query is the advanced one
     * @param username
     * @return
     */
    List<User> findByUsernameContainingIgnoreCase(String username);

    /**
     * This Query returns a user with the provided id
     * @param id
     * @return
     */
    Optional<User> findById(Long id);

    /**
     * This Query returns a user with the provided username
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     * This Query returns a user with the provided email
     * @param email
     * @return
     */
    Optional<User>findByEmail(String email);

    /**
     * This Query returns a user with the provided token
     * @param token
     * @return
     */
    Optional<User> findByResetPasswordToken(String token);

    /**
     * This Query returns a user with the provided verification code
     * @param code
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);

    /**
     * This Query returns the amount of followed accounts a user has
     * @param id
     * @return
     */
    @Query(value="SELECT COUNT(*) FROM users_followed WHERE user_id = ?1 GROUP BY user_id",nativeQuery = true)
    long countFollowed (long id);

    @Query(value="SELECT COUNT(*) FROM users_followers WHERE user_id = ?1 GROUP BY user_id",nativeQuery = true)
    long countFollowers(Long id);

    @Query(value="SELECT * FROM users WHERE enabled = false",nativeQuery = true)
    List<User> findBanned (long id);

    @Query(value = "SELECT users.* FROM users_followers JOIN users ON followers_id=id WHERE user_id = ?1 LIMIT ?2,?3",nativeQuery = true)
    List<User> findFollowers(Long id, int from, int size);

    /**
     * This Query returns the banned users
     * @return
     */
    @Query(value="SELECT * FROM users WHERE type = 'BANNED' LIMIT ?1,?2",nativeQuery = true)
    List<User> findBanned (int init, int size);

    /**
     * This Query returns the verified users
     * @return
     */
    @Query(value="SELECT * FROM users WHERE type = 'VERIFIED' LIMIT ?1,?2",nativeQuery = true)
    List<User> findVerified (int init, int size);

    /**
     * This Query returns the users that are neither VERIFIED nor BANNED
     * @return
     */
    @Query(value="SELECT * FROM users WHERE type<>'VERIFIED' AND type<>'BANNED' LIMIT ?1,?2",nativeQuery = true)
    List<User> findNotVerifiedNotBanned(int init, int size);

    @Query(value = "SELECT join_date, COUNT(*) AS new_people FROM users GROUP BY join_date ORDER BY join_date DESC LIMIT 0,5",nativeQuery = true)
    List<Tuple> countByLast5JoinDate();

    @Query(value = "SELECT users.* FROM users_followed JOIN users ON followed_id=id WHERE user_id = ?1 LIMIT ?2,?3 ",nativeQuery = true)
    List<User> findFollowed(Long id, int from, int size);

    @Query("SELECT DISTINCT u FROM User u JOIN u.followers f JOIN f.followers f2 WHERE f2.id = :userId AND u.id NOT IN (SELECT f2.id FROM User u2 JOIN u2.followed f2 WHERE u2.id = :userId) AND u.id <> :userId")
    List<User> findRecommendedUsers(@Param("userId") Long userId);


}

