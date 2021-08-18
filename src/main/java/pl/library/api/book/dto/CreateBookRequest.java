package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapter.mysql.book.Book;

@Value
public class CreateBookRequest {
    String name;

    public Book toBook() {
        return Book.builder()
                .name(this.name)
                .build();
    }
}
