package pl.library.api.user.dto;

import lombok.Value;
import pl.library.adapter.mysql.book.Book;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.adapter.mysql.user.User;

@Value
public class GetBorrowResponse {
    User user;
    Book book;

    public GetBorrowResponse(Borrow borrow) {
        this.user = borrow.getUser();
        this.book = borrow.getBook();
    }
}
