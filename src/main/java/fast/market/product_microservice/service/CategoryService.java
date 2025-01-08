package fast.market.product_microservice.service;

import fast.market.product_microservice.dto.CategoryDto;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(Long categoryId);
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
    void deleteCategory(Long categoryId);
}
