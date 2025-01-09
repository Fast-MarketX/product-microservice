package fast.market.product_microservice.service.ServiceImpl;

import fast.market.product_microservice.dto.CategoryDto;
import fast.market.product_microservice.dto.LinkProductToCategoryDto;
import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.entity.Category;
import fast.market.product_microservice.entity.Product;
import fast.market.product_microservice.exception.category.CategoryNotFoundException;
import fast.market.product_microservice.exception.product.ProductAlreadyExistsException;
import fast.market.product_microservice.exception.product.ProductNotFoundException;
import fast.market.product_microservice.mapper.CategoryMapper;
import fast.market.product_microservice.mapper.CategoryMapperImpl;
import fast.market.product_microservice.mapper.ProductMapper;
import fast.market.product_microservice.repository.CategoryRepository;
import fast.market.product_microservice.repository.ProductRepository;
import fast.market.product_microservice.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        if(productRepository.existsByProductName(productDto.getProductName())){
            throw new ProductAlreadyExistsException("Product with the given name already exists.");
        }
        Set<Category> categoriesToSave = new HashSet<>();
        for (CategoryDto categoryDto : productDto.getCategories()){
            Category existingCategory = categoryRepository.findByCategoryName(categoryDto.getCategoryName()).orElse(null);

            if(existingCategory == null) {
                Category categoryToSave = categoryMapper.CategoryDtoToCategory(categoryDto);
                categoryRepository.save(categoryToSave);
                existingCategory = categoryToSave;
            }
            categoriesToSave.add(existingCategory);
        }
        Product createdProduct = productMapper.ProductDtoToProduct(productDto);

        createdProduct.setCategories(categoriesToSave);

        productRepository.save(createdProduct);

        return productMapper.ProductToProductDto(createdProduct);
    }

    @Transactional
    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findByIdWithCategories(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with the given ID does not exist."));
        return productMapper.ProductToProductDto(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with the given ID does not exist."));

        BeanUtils.copyProperties(productDto, existingProduct, "productId");

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.ProductToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        if(!productRepository.existsById(productId)){
            throw new ProductNotFoundException("Product with the given ID does not exist.");
        }
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto linkProductToCategory(LinkProductToCategoryDto linkProductToCategoryDto) {
        Product product = productRepository.findById(linkProductToCategoryDto.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product with the given ID does not exist."));
        Category category = categoryRepository.findById(linkProductToCategoryDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category with the given ID does not exist."));

        product.getCategories().add(category);
        category.getProducts().add(product);

        productRepository.save(product);
        categoryRepository.save(category);

        return productMapper.ProductToProductDto(product);
    }
}
