package pl.library.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapter.mysql.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
