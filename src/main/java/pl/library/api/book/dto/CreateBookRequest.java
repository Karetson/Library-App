package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapter.mysql.book.Book;

@Value
public class CreateBookRequest {
    String title;
    String author;

    public Book toBook() {
        return Book.builder()
                .title(this.title)
                .author(this.author)
                .build();
    }
}
