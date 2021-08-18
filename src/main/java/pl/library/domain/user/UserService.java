package pl.library.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.library.adapter.mysql.role.Role;
import pl.library.adapter.mysql.user.User;
import pl.library.domain.role.RoleRepository;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User registration(User user) throws ValidationException {
        if (!validatePassword(user.getPassword())) {
            throw new ValidationException("Incorrect password");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Role role = roleRepository.findByName("USER");
            user.setRoles(new HashSet<>(Collections.singletonList(role)));
        }

        return userRepository.save(user);
    }
}
