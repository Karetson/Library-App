package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapter.mysql.role.Role;
import pl.library.adapter.mysql.user.User;

import java.util.Set;
import java.util.stream.Collectors;

@Value
public class GetUserResponse {
    long id;
    String username;
    Set<Role> roles;
    Set<GetBorrowResponse> borrowed;

    public GetUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.borrowed = user.getBorrowed().stream().map(GetBorrowResponse::new).collect(Collectors.toSet());
    }
}
