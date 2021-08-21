package pl.library.domain.user.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String exception) {
        super(exception);
    }
}
