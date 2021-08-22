package pl.library.api.borrow.dto;

import lombok.Value;
import pl.library.adapter.mysql.book.Book;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.adapter.mysql.user.User;

@Value
public class CreateBorrowRequest {
    User user;
    Book book;

    public Borrow toBorrow() {
        return Borrow.builder()
                .user(this.user)
                .book(this.book)
                .build();
    }
}
