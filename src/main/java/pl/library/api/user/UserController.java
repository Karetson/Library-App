package pl.library.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.adapter.mysql.user.User;
import pl.library.api.user.dto.CreateUserRequest;
import pl.library.api.user.dto.CreateUserResponse;
import pl.library.domain.user.UserService;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<User> getAllUsers() throws UserNotFoundException {
        return userService.getAllUsers();
    }

    @GetMapping("/borrows/{id}")
    public Set<Borrow> getAllUsersBorrowedBooksByUserId(@PathVariable Long id) {
        return userService.getAllUsersBorrowedBooksByUserId(id);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse registration(@Valid @RequestBody CreateUserRequest createUserRequest)
            throws ValidationException, UserExistsException {
        User newUser = userService.registration(createUserRequest.toUser());

        return new CreateUserResponse(newUser.getId());
    }
}
