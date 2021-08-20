package pl.library.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message);

        return error;
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorResponse handleUserExistsException(UserExistsException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message);

        return error;
    }
}
