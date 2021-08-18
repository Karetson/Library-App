package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapter.mysql.user.User;

@Value
public class CreateUserRequest {
    String username;
    String password;

    public User toUser() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .build();
    }
}
