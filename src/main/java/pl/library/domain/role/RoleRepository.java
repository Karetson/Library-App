package pl.library.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapter.mysql.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
