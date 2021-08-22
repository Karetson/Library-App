package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.adapter.mysql.role.Role;
import pl.library.adapter.mysql.user.User;

import java.util.Set;

@Value
public class GetUserResponse {
    long id;
    String username;
    Set<Role> roles;
    Set<Borrow> borrowed;
    int limits;

    public GetUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.borrowed = user.getBorrowed();
        this.limits = user.getLimits();
    }
}
