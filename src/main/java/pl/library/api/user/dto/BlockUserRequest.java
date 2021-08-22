package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapter.mysql.role.Role;
import pl.library.adapter.mysql.user.User;

import java.util.Set;

@Value
public class BlockUserRequest {
    Set<Role> roles;

    public User toUser() {
        return User.builder()
                .roles(this.roles)
                .build();
    }
}
