package pl.library.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

    public static boolean validatePassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);

        return matcher.find();
    }

    public List<User> getAllUsers() throws UserNotFoundException {
        List<User> list = userRepository.findAll();

        if (list.isEmpty()) {
            throw new UserNotFoundException("There is no users in database");
        } else {
            return list;
        }
    }

    @Transactional
    public User registration(User user) throws ValidationException, UserExistsException {

        if (!validatePassword(user.getPassword())) {
            throw new ValidationException("Password must have at least 8 characters, " +
                    "uppercase and lowercase letters, numbers and at least one special character, e.g., ! @ # ? ]");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserExistsException("User with '" + user.getUsername() + "' username already exists!");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Role role = roleRepository.findByName("USER").orElseThrow();
            user.setRoles(new HashSet<>(Collections.singletonList(role)));
        }

        return userRepository.save(user);
    }
}
