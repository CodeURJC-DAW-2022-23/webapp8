package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.userManagement.User;
import com.fasterxml.jackson.annotation.JsonView;
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

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User>findByEmail(String email);
    Optional<User> findByResetPasswordToken(String token);
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);
    @Query(value="SELECT \"profile_picture\" FROM \"users\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findProfileImageByUserId(Long id);
    @Query(value="SELECT \"profile_banner\" FROM \"users\" WHERE \"id\" = ?1",nativeQuery = true)
    Blob findProfileBannerByUserId(Long id);

}
