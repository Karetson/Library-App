package pl.library.api.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.library.api.error.ErrorResponse;
import pl.library.domain.category.exception.CategoryNotFoundException;

@RestControllerAdvice
public class CategoryControllerAdvice {
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleCategoryNotFoundException(CategoryNotFoundException exception) {
        String message = exception.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(message);

        return error;
    }
}
