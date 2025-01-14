package fast.market.product_microservice.controller;

import fast.market.product_microservice.dto.CategoryDto;
import fast.market.product_microservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private CategoryDto categoryDto;
    @BeforeEach
    public void setUp(){
        categoryDto = new CategoryDto("test category", "test description");
    }

    @Test
    public void createCategory_ShouldReturnCreatedCategory_WhenValidRequest(){
        when(categoryService.createCategory(categoryDto)).thenReturn(categoryDto);

        ResponseEntity<CategoryDto> response = categoryController.createCategory(categoryDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(categoryDto);
    }

    @Test
    public void getCategoryById_ShouldReturnCategory_WhenCategoryExists() {
        when(categoryService.getCategoryById(1L)).thenReturn(categoryDto);

        ResponseEntity<CategoryDto> response = categoryController.getCategoryById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(categoryDto);
    }

    @Test
    public void updateCategory_ShouldReturnUpdatedCategory_WhenCategoryExists() {
        when(categoryService.updateCategory(categoryDto, 1L)).thenReturn(categoryDto);

        ResponseEntity<CategoryDto> response = categoryController.updateCategory(categoryDto, 1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(categoryDto);
    }

    @Test
    public void deleteCategory_ShouldReturnNoContent_WhenCategoryDeleted() {
        doNothing().when(categoryService).deleteCategory(1L);

        ResponseEntity<String> response = categoryController.deleteCategory(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
