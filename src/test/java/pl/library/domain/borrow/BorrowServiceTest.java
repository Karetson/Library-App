package pl.library.domain.borrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.library.adapter.mysql.book.Book;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.adapter.mysql.role.Role;
import pl.library.adapter.mysql.user.User;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.borrow.exception.UserIsBlockedException;
import pl.library.domain.borrow.exception.UserLimitException;
import pl.library.domain.borrow.repository.BorrowRepository;
import pl.library.domain.role.repository.RoleRepository;
import pl.library.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BorrowServiceTest {
    static final long ID = 1L;

    BorrowService systemUnderTest;
    @Mock
    BorrowRepository borrowRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;

    Set<Role> roles = new HashSet<>();
    Set<Borrow> borrows = new HashSet<>();
    User user = new User(ID, "username", "password", roles, borrows, 5);
    Book book = new Book(ID, "title", "author", true, null);
    Role role = new Role(ID, "BLOCKED");
    Borrow borrow = new Borrow(ID, null, user, book);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new BorrowService(borrowRepository, userRepository, bookRepository, roleRepository);
    }

    @Test
    void shouldBorrowBook() throws UserIsBlockedException, UserLimitException {
        // given
        List<Borrow> listOfBorrows = new ArrayList<>(List.of(borrow));
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(roleRepository.findByName("BLOCKED")).thenReturn(Optional.of(new Role()));
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(borrowRepository.saveAll(listOfBorrows)).thenReturn(listOfBorrows);
        // when
        List<Borrow> borrows = systemUnderTest.borrowBook(listOfBorrows);
        // then
        assertThat(borrows).containsExactly(borrow);
    }

    @Test
    void shouldNotBorrowBookWhenUserIsBlocked() {
        // given
        List<Borrow> listOfBorrows = new ArrayList<>(List.of(borrow));
        user.setRoles(new HashSet<>(List.of(role)));
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(roleRepository.findByName("BLOCKED")).thenReturn(Optional.of(role));
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.borrowBook(listOfBorrows)).isInstanceOf(UserIsBlockedException.class);
    }

    @Test
    void shouldNotBorrowBookWhenUserReachBorrowLimit() {
        // given
        List<Borrow> listOfBorrows = new ArrayList<>(List.of(borrow));
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.borrowBook(listOfBorrows)).isInstanceOf(UserLimitException.class);
    }

    @Test
    void shouldReturnBook() throws BookNotFoundException {
        // given
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        // when
        systemUnderTest.returnBook(ID);
        // then
        verify(borrowRepository).deleteById(ID);
    }

    @Test
    void shouldNotReturnBookWhenBookIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.returnBook(ID)).isInstanceOf(BookNotFoundException.class);
    }
}