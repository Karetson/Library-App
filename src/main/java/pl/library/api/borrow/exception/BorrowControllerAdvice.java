package pl.library.api.borrow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.borrow.exception.UserIsBlockedException;

@RestControllerAdvice
public class BorrowControllerAdvice {
    @ExceptionHandler(UserIsBlockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleUserIsBlockedException(UserIsBlockedException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message);

        return error;
    }
}
