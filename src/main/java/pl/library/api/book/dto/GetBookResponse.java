package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapter.mysql.book.Book;

@Value
public class GetBookResponse {
    long id;
    String title;
    String author;
    boolean available;


    public GetBookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.available = book.isAvailable();
    }
}
