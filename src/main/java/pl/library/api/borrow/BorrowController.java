package pl.library.api.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.borrow.BorrowService;
import pl.library.domain.borrow.exception.UserLimitException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Borrow> addBorrow(@Valid @RequestBody List<Borrow> borrows)
            throws UserLimitException {
        return borrowService.addBorrow(borrows);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBorrow(@PathVariable Long id) throws BookNotFoundException {
        borrowService.deleteBorrow(id);
    }
}
