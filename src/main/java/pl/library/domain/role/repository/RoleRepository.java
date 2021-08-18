package pl.library.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapter.mysql.role.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
