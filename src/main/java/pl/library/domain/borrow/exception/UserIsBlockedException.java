package pl.library.domain.borrow.exception;

public class UserIsBlockedException extends Exception {
    String message;

    public UserIsBlockedException(String exception) {
        super(exception);
    }
}
