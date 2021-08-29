package pl.library.domain.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public List<Borrow> borrowBook(List<Borrow> borrows) throws UserLimitException, UserIsBlockedException {
        long user_id = borrows.get(0).getUser().getId();
        int numberOfBooks = borrows.size();

        if (isUserBlocked(user_id)) {
            throw new UserIsBlockedException("You are blocked. You can not borrows any book.");
        }
        if (hasUserReachedLimit(user_id, numberOfBooks)) {
            throw new UserLimitException("You can not borrow more books.");
        }

            borrows.stream()
                    .peek(element -> {
                        element.setCreatedAt(LocalDateTime.now());
                        long book_id = element.getBook().getId();
                        Book book = bookRepository.findById(book_id).orElseThrow();
                        book.setAvailable(false);
                        bookRepository.save(book);
                    })
                    .collect(Collectors.toList());

        return borrowRepository.saveAll(borrows);
    }

    @Transactional
    public void returnBook(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("There is no such book."));
        book.setAvailable(true);
        bookRepository.save(book);
        borrowRepository.deleteById(id);
    }

    private boolean isUserBlocked(long id) {
        User user = userRepository.findById(id).orElseThrow();
        Role roleBlocked = roleRepository.findByName("BLOCKED").orElseThrow();

        return user.getRoles().contains(roleBlocked);
    }

    private boolean hasUserReachedLimit(long id, int numberOfBooks) throws UserLimitException {
        final int limit = 5;
        User user = userRepository.findById(id).orElseThrow();
        int usersLimit = limit - user.getBorrowed().size();

        return numberOfBooks > usersLimit;
    }
}
