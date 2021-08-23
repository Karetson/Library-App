package pl.library.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapter.mysql.book.Book;
import pl.library.adapter.mysql.category.Category;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.category.exception.CategoryNotFoundException;
import pl.library.domain.category.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllAvailableBooks() {
        return bookRepository.findAll().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public Book getBookByTitle(String title) throws BookNotFoundException {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException("There is no book with title: " + title));
    }

    public List<Book> getAllBooksByCategory(Long id) throws BookNotFoundException, CategoryNotFoundException {
        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("There is no category with id: " + id));

        return bookRepository.findAllByCategories(foundCategory)
                .orElseThrow(() -> new BookNotFoundException("There are no books with category: " +
                        foundCategory.getName()));
    }

    public List<Book> getAllBooksByPhrase(String phrase) throws BookNotFoundException {
        return bookRepository.findAllByPhraseLike(phrase)
                .orElseThrow(() -> new BookNotFoundException("There are no books with phrase: " + phrase));
    }

    @Transactional
    public Book addBook(Book book) throws BookExistsException {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())){
            throw new BookExistsException("Book with '" + book.getTitle() + "' title and '" +
                    book.getAuthor() + "' author already exists.");
        } else {
            return bookRepository.save(book);
        }
    }

    @Transactional
    public void deleteBook(long id) throws BookNotFoundException {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("There is no such book.");
        } else {
            bookRepository.deleteById(id);
        }
    }
}
