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
import pl.library.api.user.dto.GetBorrowResponse;
import pl.library.api.user.dto.GetUserResponse;
import pl.library.domain.user.UserService;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<GetUserResponse> getAllUsers() throws UserNotFoundException {
        List<User> listOfUsers = userService.getAllUsers();

        return listOfUsers.stream().map(GetUserResponse::new).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/borrows/{id}")
    public Set<GetBorrowResponse> getAllUsersBorrowedBooks(@PathVariable Long id) {
        Set<Borrow> borrows = userService.getAllUsersBorrowedBooks(id);

        return borrows.stream().map(GetBorrowResponse::new).collect(Collectors.toSet());
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse registration(@Valid @RequestBody CreateUserRequest createUserRequest)
            throws ValidationException, UserExistsException {
        User newUser = userService.registration(createUserRequest.toUser());

        return new CreateUserResponse(newUser.getId());
    }
}
