package pl.library.domain.borrow.exception;

public class UserIsBlockedException extends Exception {
    public UserIsBlockedException(String exception) {
        super(exception);
    }
}
