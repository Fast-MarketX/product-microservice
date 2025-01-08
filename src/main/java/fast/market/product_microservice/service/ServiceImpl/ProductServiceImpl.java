package fast.market.product_microservice.service.ServiceImpl;

import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.entity.Product;
import fast.market.product_microservice.exception.product.ProductAlreadyExistsException;
import fast.market.product_microservice.exception.product.ProductNotFoundException;
import fast.market.product_microservice.mapper.ProductMapper;
import fast.market.product_microservice.repository.ProductRepository;
import fast.market.product_microservice.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        if(productRepository.existsById(productDto.getProductId())){
            throw new ProductAlreadyExistsException("Product with the given ID already exists.");
        }
        Product createdProduct = productMapper.ProductDtoToProduct(productDto);
        productRepository.save(createdProduct);

        return productMapper.ProductToProductDto(createdProduct);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with the given ID does not exist."));

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
}
