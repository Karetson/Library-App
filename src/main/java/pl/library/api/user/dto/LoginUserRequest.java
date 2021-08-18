package pl.library.api.user.dto;

import lombok.Value;

@Value
public class LoginUserRequest {
    String username;
    String password;
}
