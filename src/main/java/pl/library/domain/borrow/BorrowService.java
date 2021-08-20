package pl.library.domain.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.domain.borrow.repository.BorrowRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;

    @Transactional
    public List<Borrow> addBorrow(List<Borrow> borrow) {
        return borrowRepository.saveAll(borrow);
    }

    @Transactional
    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }
}
