package Repository;

import Model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<RegisteredUser, UUID> {
    Optional<RegisteredUser> findById(UUID id);
}