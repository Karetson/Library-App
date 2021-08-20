package pl.library.domain.borrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapter.mysql.borrow.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
