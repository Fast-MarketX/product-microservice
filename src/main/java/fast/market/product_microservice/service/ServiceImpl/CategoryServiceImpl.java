package fast.market.product_microservice.service.ServiceImpl;

import fast.market.product_microservice.dto.CategoryDto;
import fast.market.product_microservice.entity.Category;
import fast.market.product_microservice.exception.category.CategoryAlreadyExistsException;
import fast.market.product_microservice.exception.category.CategoryNotFoundException;
import fast.market.product_microservice.mapper.CategoryMapper;
import fast.market.product_microservice.repository.CategoryRepository;
import fast.market.product_microservice.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsById(categoryDto.getCategoryId())){
            throw new CategoryAlreadyExistsException("Category with the given ID already exists");
        }
        Category createdCategory = categoryMapper.CategoryDtoToCategory(categoryDto);
        categoryRepository.save(createdCategory);

        return categoryMapper.CategoryToCategoryDto(createdCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category with the given ID does not exist."));

        return categoryMapper.CategoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category with the given ID does not exist."));

        BeanUtils.copyProperties(categoryDto, existingCategory, "categoryId");

        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.CategoryToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)){
            throw new CategoryNotFoundException("Category with the given ID does not exist.");
        }
        categoryRepository.deleteById(categoryId);
    }
}
