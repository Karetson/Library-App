package pl.library.api.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.book.exception.BookNotFoundException;

@RestControllerAdvice
public class BookControllerAdvice {
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleBookNotFoundException(BookNotFoundException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message);

        return error;
    }
}
