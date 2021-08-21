package pl.library.domain.user.exception;

public class UserExistsException extends Exception {
    public UserExistsException(String exception) {
        super(exception);
    }
}
