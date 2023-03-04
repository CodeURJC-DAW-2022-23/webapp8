package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

    /**
     * This Query returns the amount of follower accounts a user has
     * @param id
     * @return
     */
    @Query(value="SELECT COUNT(*) FROM users_followers WHERE user_id = ?1 GROUP BY user_id",nativeQuery = true)
    long countFollowers (long id);

    /**
     * This Query returns the banned users
     * @return
     */
    @Query(value="SELECT * FROM users WHERE type = 'BANNED'",nativeQuery = true)
    List<User> findBanned ();

    /**
     * This Query returns the verified users
     * @return
     */
    @Query(value="SELECT * FROM users WHERE type = 'VERIFIED'",nativeQuery = true)
    List<User> findVerified ();
}
