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

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameContainingIgnoreCase(String username);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User>findByEmail(String email);
    Optional<User> findByResetPasswordToken(String token);
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);
    @Query(value="SELECT COUNT(*) FROM users_followed WHERE user_id = ?1 GROUP BY user_id",nativeQuery = true)
    long countFollowed (long id);
    @Query(value="SELECT COUNT(*) FROM users_followers WHERE user_id = ?1 GROUP BY user_id",nativeQuery = true)
    long countFollowers (long id);
    @Query(value="SELECT * FROM users WHERE enabled = false",nativeQuery = true)
    List<User> findBanned (long id);
}
