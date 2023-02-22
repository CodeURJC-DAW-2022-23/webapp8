package Repository;

import Model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
    Optional<Tweet> findById(UUID id);
}