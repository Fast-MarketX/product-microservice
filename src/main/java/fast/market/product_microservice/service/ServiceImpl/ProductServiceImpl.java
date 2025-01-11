package fast.market.product_microservice.service.ServiceImpl;

import fast.market.product_microservice.dto.ProductCategoryLinkDto;
import fast.market.product_microservice.dto.CategoryDto;
import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.entity.Category;
import fast.market.product_microservice.entity.Product;
import fast.market.product_microservice.exception.category.CategoryDuplicationException;
import fast.market.product_microservice.exception.category.CategoryNotFoundException;
import fast.market.product_microservice.exception.product.ProductAlreadyExistsException;
import fast.market.product_microservice.exception.product.ProductNotFoundException;
import fast.market.product_microservice.mapper.CategoryMapper;
import fast.market.product_microservice.mapper.ProductMapper;
import fast.market.product_microservice.repository.CategoryRepository;
import fast.market.product_microservice.repository.ProductRepository;
import fast.market.product_microservice.service.ProductService;
import lombok.SneakyThrows;
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

    String productAlreadyExistsExMessage = "Product with the given name already exists.";
    String productNotFoundExMessage = "Product with the given ID does not exist.";

    String categoryNotFoundExMessage = "Category with the given ID does not exist";
    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        if(productRepository.existsByProductName(productDto.getProductName())){
            throw new ProductAlreadyExistsException(productAlreadyExistsExMessage);
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
                .orElseThrow(() -> new ProductNotFoundException(productNotFoundExMessage));
        return productMapper.ProductToProductDto(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productNotFoundExMessage));

        BeanUtils.copyProperties(productDto, existingProduct, "productId");

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.ProductToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        if(!productRepository.existsById(productId)){
            throw new ProductNotFoundException(productNotFoundExMessage);
        }
        productRepository.deleteById(productId);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void linkProductToCategory(ProductCategoryLinkDto linkCategoryToProductDto){
        Product product = productRepository.findById(linkCategoryToProductDto.getProductId()).orElseThrow(() -> new ProductNotFoundException(productNotFoundExMessage));
        Category category = categoryRepository.findById(linkCategoryToProductDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(categoryNotFoundExMessage));

        if(!product.getCategories().contains(category)){
            product.getCategories().add(category);
            productRepository.save(product);
        }else{
            throw new CategoryDuplicationException("This Category is already linked to the product.");
        }
    }

    @Override
    @Transactional
    public void removeCategoryFromProduct(ProductCategoryLinkDto productCategoryLinkDto) {
        Product product = productRepository.findById(productCategoryLinkDto.getProductId()).orElseThrow(() -> new ProductNotFoundException(productNotFoundExMessage));
        Category category = categoryRepository.findById(productCategoryLinkDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(categoryNotFoundExMessage));

        if(product.getCategories().contains(category)){
            product.getCategories().remove(category);
            productRepository.save(product);
        }else{
            throw new CategoryNotFoundException(categoryNotFoundExMessage);
        }
    }
}
