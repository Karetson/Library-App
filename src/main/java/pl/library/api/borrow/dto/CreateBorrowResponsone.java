package pl.library.api.borrow.dto;

import lombok.Value;
import pl.library.adapter.mysql.borrow.Borrow;

@Value
public class CreateBorrowResponsone {
    long id;

    public CreateBorrowResponsone(Borrow borrow) {
        this.id = borrow.getId();
    }
}
