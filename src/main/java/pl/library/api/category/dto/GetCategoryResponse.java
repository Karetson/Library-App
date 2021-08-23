package pl.library.api.category.dto;

import lombok.Value;
import pl.library.adapter.mysql.category.Category;

@Value
public class GetCategoryResponse {
    long id;
    String name;

    public GetCategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
