package pl.library.domain.borrow.exception;

public class UserLimitException extends Exception{
    public UserLimitException(String exception) {
        super(exception);
    }
}
