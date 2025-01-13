package fast.market.product_microservice.controller;

import fast.market.product_microservice.dto.ProductCategoryLinkDto;
import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        productService.createProduct(productDto);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto productDto = productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId){
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product was successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/linkProductToCategory")
    public ResponseEntity<String> linkProductToCategory(@RequestBody ProductCategoryLinkDto linkCategoryToProductDto) {
        productService.linkProductToCategory(linkCategoryToProductDto);
        return new ResponseEntity<>("Product was linked to the category", HttpStatus.OK);
    }

    @DeleteMapping("/removeCategoryFromProduct")
    public ResponseEntity<String> removeCategoryFromProduct(@RequestBody ProductCategoryLinkDto productCategoryLinkDto) {
        productService.removeCategoryFromProduct(productCategoryLinkDto);
        return new ResponseEntity<>("Product was successfully deleted from category", HttpStatus.NO_CONTENT);
    }
}
