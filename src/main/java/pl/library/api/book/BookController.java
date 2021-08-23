package pl.library.api.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapter.mysql.book.Book;
import pl.library.api.book.dto.CreateBookRequest;
import pl.library.api.book.dto.CreateBookResponse;
import pl.library.api.book.dto.GetBookResponse;
import pl.library.domain.book.BookService;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.category.exception.CategoryNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<GetBookResponse> getAllBooks() {
        List<Book> foundBooks = bookService.getAllBooks();
        return foundBooks.stream()
                .map(GetBookResponse::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('USER', 'BLOCKED')")
    @GetMapping("/getAllAvailable")
    public List<GetBookResponse> getAllAvailableBooks() {
        List<Book> foundBooks = bookService.getAllAvailableBooks();
        return foundBooks.stream()
                .map(GetBookResponse::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('USER', 'BLOCKED')")
    @GetMapping("/getAllByCategory/{id}")
    public List<GetBookResponse> getAllBooksByCategory(@PathVariable Long id)
            throws CategoryNotFoundException, BookNotFoundException {
        List<Book> foundBooks = bookService.getAllBooksByCategory(id);

        return foundBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('USER', 'BLOCKED')")
    @GetMapping("/getAllByPhrase")
    public List<GetBookResponse> getAllBooksByPhrase(@RequestParam String phrase) throws BookNotFoundException {
        List<Book> foundBooks = bookService.getAllBooksByPhrase(phrase);

        return foundBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('USER', 'BLOCKED')")
    @GetMapping("/getByTitle")
    public GetBookResponse getBookByTitle(@RequestParam String title) throws BookNotFoundException {
        Book foundBook = bookService.getBookByTitle(title);

        return new GetBookResponse(foundBook);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookResponse addBook(@Valid @RequestBody CreateBookRequest createBookRequest)
            throws BookExistsException {
        Book newBook = bookService.addBook(createBookRequest.toBook());

        return new CreateBookResponse(newBook.getId());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) throws BookNotFoundException {
        bookService.deleteBook(id);
    }
}
