package fast.market.product_microservice.service;

import fast.market.product_microservice.dto.ProductCategoryLinkDto;
import fast.market.product_microservice.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(ProductDto productDto, Long productId);
    void deleteProduct(Long productId);

    void linkProductToCategory(ProductCategoryLinkDto linkCategoryToProductDto);
    void removeCategoryFromProduct(ProductCategoryLinkDto productCategoryLinkDto);
}
