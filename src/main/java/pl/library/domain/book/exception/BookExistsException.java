package pl.library.domain.book.exception;

public class BookExistsException extends Exception{
    public BookExistsException(String exception) {
        super(exception);
    }
}
