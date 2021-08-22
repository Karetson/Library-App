package pl.library.api.book.dto;

import lombok.Value;
import pl.library.adapter.mysql.book.Book;
import pl.library.adapter.mysql.category.Category;

import java.util.Set;

@Value
public class CreateBookRequest {
    String title;
    String author;
    Set<Category> categories;

    public Book toBook() {
        return Book.builder()
                .title(this.title)
                .author(this.author)
                .categories(this.categories)
                .build();
    }
}
