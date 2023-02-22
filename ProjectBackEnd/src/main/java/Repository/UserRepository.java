package com.TwitterClone.ProjectBackEnd.Repository;

import com.TwitterClone.ProjectBackEnd.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(UUID id);
}