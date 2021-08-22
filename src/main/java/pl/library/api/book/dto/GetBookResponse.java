package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapter.mysql.book.Book;
import pl.library.adapter.mysql.category.Category;

import java.util.Set;

@Value
public class GetBookResponse {
    long id;
    String title;
    String author;
    boolean available;
    Set<Category> categories;


    public GetBookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.available = book.isAvailable();
        this.categories = book.getCategories();
    }
}
