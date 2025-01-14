package fast.market.product_microservice.service;

import fast.market.product_microservice.dto.CategoryDto;
import fast.market.product_microservice.entity.Category;
import fast.market.product_microservice.mapper.CategoryMapper;
import fast.market.product_microservice.repository.CategoryRepository;
import fast.market.product_microservice.service.ServiceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    private CategoryDto categoryDto;
    private Category category;

    @BeforeEach
    public void setUp(){
        categoryDto = new CategoryDto("test category", "test description");
        category = new Category(1L, "test category", "test description", new HashSet<>());
    }

    @Test
    public void createCategory_ShouldReturnCategory_WhenCategoryDoesNotExist(){
        when(categoryRepository.findByCategoryName(categoryDto.getCategoryName())).thenReturn(Optional.empty());
        when(categoryMapper.CategoryDtoToCategory(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.CategoryToCategoryDto(category)).thenReturn(categoryDto);

        CategoryDto createdCategory = categoryServiceImpl.createCategory(categoryDto);

        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getCategoryName()).isEqualTo(categoryDto.getCategoryName());
    }

    @Test
    public void getCategoryById_ShouldReturnCategory_WhenCategoryExists(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.CategoryToCategoryDto(category)).thenReturn(categoryDto);

        CategoryDto foundCategory = categoryServiceImpl.getCategoryById(1L);

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getCategoryName()).isEqualTo("test category");
    }

    @Test
    public void updateCategory_ShouldUpdateCategory_WhenCategoryExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.CategoryDtoToCategory(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.CategoryToCategoryDto(category)).thenReturn(categoryDto);

        CategoryDto updatedCategory = categoryServiceImpl.updateCategory (categoryDto, 1L);

        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getCategoryName()).isEqualTo(categoryDto.getCategoryName());
    }

    @Test
    public void deleteCategory_ShouldDeleteCategory_WhenCategoryExists() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryServiceImpl.deleteCategory(1L);

        verify(categoryRepository).deleteById(1L);
    }
}
