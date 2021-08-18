package pl.library.api.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapter.mysql.book.Book;
import pl.library.api.book.dto.CreateBookRequest;
import pl.library.api.book.dto.CreateBookResponse;
import pl.library.api.book.dto.GetBookResponse;
import pl.library.domain.book.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/getAll")
    public List<GetBookResponse> getAllBooks() {
        List<Book> foundBooks = bookService.getAllBooks();
        return foundBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookResponse addBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        Book newBook = bookService.addBook(createBookRequest.toBook());

        return new CreateBookResponse(newBook.getId());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
