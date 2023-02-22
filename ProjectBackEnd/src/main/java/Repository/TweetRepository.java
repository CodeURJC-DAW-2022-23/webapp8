package com.TwitterClone.ProjectBackEnd.Repository;

import com.TwitterClone.ProjectBackEnd.Model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(UUID id);
}