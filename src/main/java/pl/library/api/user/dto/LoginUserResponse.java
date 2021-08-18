package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapter.mysql.role.Role;

import java.util.Set;

@Value
public class LoginUserResponse {
    long id;
    String username;
    Set<Role> roles;
}
