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
    public List<Borrow> borrowBook(List<Borrow> borrow) throws UserLimitException, UserIsBlockedException {
        final int limit = 5;
        long user_id = borrow.get(0).getUser().getId();
        User user = userRepository.findById(user_id).orElseThrow();
        Role roleBlocked = roleRepository.findByName("BLOCKED").orElseThrow();

        if (user.getRoles().contains(roleBlocked)) {
            throw new UserIsBlockedException("You are blocked. You can not borrow any book.");
        } else if (user.getBorrowed().size() == limit) {
            throw new UserLimitException("You have reached your borrowing limit of 5 books!");
        }

        borrow.stream()
                .peek(element -> {
                    long book_id = element.getBook().getId();
                    Book book = bookRepository.findById(book_id).orElseThrow();
                    book.setAvailable(false);
                    bookRepository.save(book);

                })
                .collect(Collectors.toList());

        return borrowRepository.saveAll(borrow);
    }

    @Transactional
    public void returnBook(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("There is no such book."));
        book.setAvailable(true);
        bookRepository.save(book);
        borrowRepository.deleteById(id);
    }
}
