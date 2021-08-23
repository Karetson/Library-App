package pl.library.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.adapter.mysql.role.Role;
import pl.library.adapter.mysql.user.User;
import pl.library.domain.role.repository.RoleRepository;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;
import pl.library.domain.user.repository.UserRepository;

import javax.xml.bind.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {
    static final long ID = 1L;

    UserService systemUnderTest;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    Role role = new Role();
    Borrow borrow = new Borrow();
    Set<Borrow> borrows = new HashSet<>(List.of(borrow));
    User user = new User(ID, "username", "Password!23", null, borrows);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new UserService(userRepository, roleRepository, bCryptPasswordEncoder);
    }

    @Test
    void shouldReturnAllUsers() throws UserNotFoundException {
        // given
        when(userRepository.findAll()).thenReturn(List.of(user));
        // when
        List<User> allUsers = systemUnderTest.getAllUsers();
        // then
        assertThat(allUsers).containsExactly(user);
    }

    @Test
    void shouldNotReturnAllUsersWhenUsersNotFound() {
        // given
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllUsers()).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldReturnAllBorrowedBooksByUser() {
        // given
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        // when
        Set<Borrow> allUsersBorrowedBooks = systemUnderTest.getAllUsersBorrowedBooks(ID);
        // then
        assertThat(allUsersBorrowedBooks).containsExactly(borrow);
    }

    @Test
    void shouldRegisterUser() throws UserExistsException, ValidationException {
        // given
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);
        // when
        User registeredUser = systemUnderTest.registration(user);
        // then
        assertThat(registeredUser).isEqualTo(user);
    }

    @Test
    void shouldNotRegisterUserWhenPasswordIsIncorrect() {
        // given
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(role));
        user.setPassword("password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.registration(user)).isInstanceOf(ValidationException.class);
    }

    @Test
    void shouldNotRegisterUserWhenUserIsAlreadyExists() {
        // given
        when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.registration(user)).isInstanceOf(UserExistsException.class);
    }

    @Test
    void shouldBlockUser() throws UserNotFoundException, UserExistsException {
        // given
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);
        // when
        User blockedUser = systemUnderTest.blockUser(ID);
        // then
        assertThat(blockedUser).isEqualTo(user);
    }

    @Test
    void shouldNotBlockUserWhenUserIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.blockUser(ID)).isInstanceOf(UserNotFoundException.class);
    }
}