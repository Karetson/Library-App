package pl.library.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapter.mysql.book.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
