package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapter.mysql.book.Book;

@Value
public class GetBookResponse {
    long id;
    String name;

    public GetBookResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
    }
}
