package pl.library.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.library.adapter.mysql.book.Book;
import pl.library.adapter.mysql.category.Category;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    Optional<List<Book>> findAllByCategories(Category category);

    @Query(value = "SELECT b FROM books b WHERE b.title LIKE %:phrase% OR b.author LIKE %:phrase% ORDER BY b.title ASC")
    Optional<List<Book>> findAllByPhraseLike(@Param("phrase") String phrase);
}
