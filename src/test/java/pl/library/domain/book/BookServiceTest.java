package pl.library.domain.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.library.adapter.mysql.book.Book;
import pl.library.adapter.mysql.category.Category;
import pl.library.domain.book.exception.BookExistsException;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.category.exception.CategoryNotFoundException;
import pl.library.domain.category.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BookServiceTest {
    static final long ID = 1L;

    BookService systemUnderTest;
    @Mock
    BookRepository bookRepository;
    @Mock
    CategoryRepository categoryRepository;

    Set<Category> categories = Set.of(new Category(1L, "Category"));
    Book book = new Book(1L, "title", "author", true, categories);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new BookService(bookRepository, categoryRepository);
    }

    @Test
    void shouldReturnAllBooks() {
        // given
        when(bookRepository.findAll()).thenReturn(List.of(book));
        // when
        List<Book> allBooks = systemUnderTest.getAllBooks();
        // then
        assertThat(allBooks).containsExactly(book);
    }

    @Test
    void shouldReturnAllAvailableBooks() {
        // given
        when(bookRepository.findAll()).thenReturn(List.of(book));
        // when
        List<Book> allAvailableBooks = systemUnderTest.getAllAvailableBooks();
        // then
        assertThat(allAvailableBooks).containsExactly(book);
    }

    @Test
    void shouldReturnBookByTitle() throws BookNotFoundException {
        // given
        when(bookRepository.findByTitle(any(String.class))).thenReturn(Optional.of(book));
        // when
        Book test = systemUnderTest.getBookByTitle("test");
        // then
        assertThat(test).isEqualTo(book);
    }

    @Test
    void shouldNotReturnBookByTitleWhenBookIsNotFound() {
        // given
        String title = "test";
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getBookByTitle(title)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldReturnAllBooksByCategory() throws CategoryNotFoundException, BookNotFoundException {
        // given
        when(categoryRepository.findById(ID)).thenReturn(Optional.of(new Category()));
        when(bookRepository.findAllByCategories(any(Category.class))).thenReturn(Optional.of(List.of(book)));
        // when
        List<Book> allBooksByCategory = systemUnderTest.getAllBooksByCategory(ID);
        // then
        assertThat(allBooksByCategory).containsExactly(book);
    }

    @Test
    void shouldNotReturnAllBooksByCategoryWhenCategoryIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBooksByCategory(ID)).isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void shouldNotReturnAllBooksByCategoryWhenBookIsNotFound() {
        // given
        when(categoryRepository.findById(ID)).thenReturn(Optional.of(new Category()));
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBooksByCategory(ID)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldReturnAllBooksByPhrase() throws BookNotFoundException {
        // given
        String phrase = "ar";
        when(bookRepository.findAllByPhraseLike(any(String.class))).thenReturn(Optional.of(List.of(book)));
        // when
        List<Book> allBooksByPhrase = systemUnderTest.getAllBooksByPhrase(phrase);
        // then
        assertThat(allBooksByPhrase).containsExactly(book);
    }

    @Test
    void shouldNotReturnAllBooksByPhraseWhenBookIsNotFound() {
        // given
        String phrase = "test";
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getAllBooksByPhrase(phrase)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldAddBook() throws BookExistsException {
        // given
        when(bookRepository.save(book)).thenReturn(book);
        // when
        Book newBook = systemUnderTest.addBook(this.book);
        // then
        assertThat(newBook).isEqualTo(book);
    }

    @Test
    void shouldNotAddBookWhenBookAlreadyExists() {
        // given
        when(bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())).thenReturn(true);
        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addBook(book)).isInstanceOf(BookExistsException.class);
    }

    @Test
    void shouldDeleteBook() throws BookNotFoundException {
        // given
        when(bookRepository.existsById(ID)).thenReturn(true);
        // when
        systemUnderTest.deleteBook(ID);
        // then
        verify(bookRepository).deleteById(ID);
    }

    @Test
    void shouldNotDeleteBookWhenBookIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.deleteBook(ID)).isInstanceOf(BookNotFoundException.class);
    }
}