package pl.library.domain.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.adapter.mysql.user.User;
import pl.library.domain.borrow.exception.UserIsBlockedException;
import pl.library.domain.borrow.repository.BorrowRepository;
import pl.library.domain.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Borrow> addBorrow(List<Borrow> borrow) throws UserIsBlockedException {
        long user_id = borrow.get(0).getUser().getId();
        User user = userRepository.findById(user_id).orElseThrow();

        if (user.isBlocked()) {
            throw new UserIsBlockedException("You are blocked! You can not borrow any book.");
        }

        return borrowRepository.saveAll(borrow);
    }

    @Transactional
    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }
}
