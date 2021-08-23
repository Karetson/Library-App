package pl.library.api.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import pl.library.adapter.mysql.borrow.Borrow;
import pl.library.api.book.dto.GetBookResponse;

import java.time.LocalDateTime;

@Value
public class GetBorrowResponse {
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime createdAt;
    GetBookResponse book;

    public GetBorrowResponse(Borrow borrow) {
        this.createdAt = borrow.getCreatedAt();
        this.book = new GetBookResponse(borrow.getBook());
    }
}
