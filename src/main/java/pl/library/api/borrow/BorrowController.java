package pl.library.api.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.api.borrow.dto.CreateBorrowRequest;
import pl.library.api.borrow.dto.CreateBorrowResponsone;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.borrow.BorrowService;
import pl.library.domain.borrow.exception.UserLimitException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CreateBorrowResponsone> borrowBook(@Valid @RequestBody List<CreateBorrowRequest> borrows)
            throws UserLimitException {
        List<Borrow> mappedBorrows = borrows.stream().map(CreateBorrowRequest::toBorrow).collect(Collectors.toList());
        List<Borrow> listOfBorrows = borrowService.borrowBook(mappedBorrows);

        return listOfBorrows.stream().map(CreateBorrowResponsone::new).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnBook(@PathVariable Long id) throws BookNotFoundException {
        borrowService.returnBook(id);
    }
}
