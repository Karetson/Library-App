package pl.library.domain.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.library.adapter.mysql.category.Category;
import pl.library.domain.category.repository.CategoryRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CategoryServiceTest {
    CategoryService systemUnderTest;
    @Mock
    CategoryRepository categoryRepository;

    Category category = new Category();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new CategoryService(categoryRepository);
    }

    @Test
    void shouldReturnAllCategories() {
        // given
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        // when
        List<Category> allCategories = systemUnderTest.getAllCategories();
        // then
        assertThat(allCategories).containsExactly(category);
    }
}