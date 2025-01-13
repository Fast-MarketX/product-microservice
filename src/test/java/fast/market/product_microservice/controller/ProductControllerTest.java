package fast.market.product_microservice.controller;

import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private ProductDto productDto;

    @BeforeEach
    public void setUp(){
        productDto = new ProductDto("test product", "test description", 150.99, new HashSet<>());
    }

    @Test
    public void createProduct_ShouldReturnCreatedProduct_WhenValidRequest(){
        when(productService.createProduct(productDto)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.createProduct(productDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(productDto);
    }

    @Test
    public void getProductById_ShouldReturnProduct_WhenProductExists() {
        when(productService.getProductById(1L)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.getProductById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(productDto);
    }

    @Test
    public void updateProduct_ShouldReturnUpdatedProduct_WhenProductExists() {
        when(productService.updateProduct(productDto, 1L)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.updateProduct(productDto, 1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(productDto);
    }

    @Test
    public void deleteProduct_ShouldReturnNoContent_WhenProductDeleted() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<String> response = productController.deleteProduct(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
