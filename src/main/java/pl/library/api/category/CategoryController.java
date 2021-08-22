package pl.library.api.category;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapter.mysql.category.Category;
import pl.library.api.category.dto.GetCategoryResponse;
import pl.library.domain.category.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<GetCategoryResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.stream().map(GetCategoryResponse::new).collect(Collectors.toList());
    }
}
