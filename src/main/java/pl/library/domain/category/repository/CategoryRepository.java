package pl.library.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapter.mysql.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
