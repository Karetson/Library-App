package pl.library.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.adapter.mysql.role.Role;
import pl.library.adapter.mysql.user.User;
import pl.library.domain.role.repository.RoleRepository;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;
import pl.library.domain.user.repository.UserRepository;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&+=]).*$",
                    Pattern.CASE_INSENSITIVE);

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Set<Borrow> getAllUsersBorrowedBooks(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        return user.getBorrowed();
    }

    @Transactional
    public User registration(User user) throws ValidationException, UserExistsException {
        if (!validatePassword(user.getPassword())) {
            throw new ValidationException("Password must have at least 8 characters, " +
                    "uppercase and lowercase letters, numbers and at least one special character, e.g., ! @ # ? ]");
        } else if (existsByUsername(user)) {
            throw new UserExistsException("User with '" + user.getUsername() + "' username already exists!");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Role role = roleRepository.findByName("USER").orElseThrow();
            user.setRoles(new HashSet<>(Collections.singletonList(role)));
        }

        return userRepository.save(user);
    }

    @Transactional
    public User blockUser(Long id) throws UserNotFoundException {
        User userToBlock = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is no such user"));
        Role blocked = roleRepository.findByName("BLOCKED").orElseThrow();
        userToBlock.setRoles(new HashSet<>(Collections.singletonList(blocked)));

        return userRepository.save(userToBlock);
    }

    private boolean validatePassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);

        return matcher.find();
    }

    private boolean existsByUsername(User user) {
        return userRepository.existsByUsername(user.getUsername());
    }
}
