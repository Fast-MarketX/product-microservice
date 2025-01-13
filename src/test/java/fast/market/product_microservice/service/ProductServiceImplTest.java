package fast.market.product_microservice.service;

import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.entity.Product;
import fast.market.product_microservice.mapper.ProductMapper;
import fast.market.product_microservice.repository.ProductRepository;
import fast.market.product_microservice.service.ServiceImpl.ProductServiceImpl;
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
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    public void setUp(){
        productDto = new ProductDto("test product", "test description", 150.99, new HashSet<>());
        product = new Product(1L, "test product", "test description", 150.99, new HashSet<>());
    }

    @Test
    public void createProduct_ShouldCreateProduct_WhenProductDoesNotExist(){
        when(productRepository.findByProductName(productDto.getProductName())).thenReturn(Optional.empty());
        when(productMapper.ProductDtoToProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.ProductToProductDto(product)).thenReturn(productDto);

        ProductDto createdProduct = productServiceImpl.createProduct(productDto);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getProductName()).isEqualTo(productDto.getProductName());
    }

    @Test
    public void getProductById_ShouldReturnProduct_WhenProductExists(){
        when(productRepository.findByIdWithCategories(1L)).thenReturn(Optional.of(product));
        when(productMapper.ProductToProductDto(product)).thenReturn(productDto);

        ProductDto foundProduct = productServiceImpl.getProductById(1L);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getProductName()).isEqualTo("test product");
    }

    @Test
    public void updateProduct_ShouldUpdateProduct_WhenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.ProductDtoToProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.ProductToProductDto(product)).thenReturn(productDto);

        ProductDto updatedProduct = productServiceImpl.updateProduct (productDto, 1L);

        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getProductName()).isEqualTo(productDto.getProductName());
    }

    @Test
    public void deleteProduct_ShouldDeleteProduct_WhenProductExists() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productServiceImpl.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }
}
