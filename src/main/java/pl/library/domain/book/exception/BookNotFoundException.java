package pl.library.domain.book.exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String exception) {
        super(exception);
    }
}
