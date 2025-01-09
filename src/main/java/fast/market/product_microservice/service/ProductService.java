package fast.market.product_microservice.service;

import fast.market.product_microservice.dto.LinkProductToCategoryDto;
import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.entity.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(ProductDto productDto, Long productId);
    void deleteProduct(Long productId);
    ProductDto linkProductToCategory(LinkProductToCategoryDto linkProductToCategoryDto);
}
