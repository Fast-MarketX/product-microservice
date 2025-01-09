package fast.market.product_microservice.controller;

import fast.market.product_microservice.dto.CategoryDto;
import fast.market.product_microservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

     @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId){
         CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

         return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PatchMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){
         CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);

         return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
         categoryService.deleteCategory(categoryId);

         return new ResponseEntity<>("Category was successfully deleted!", HttpStatus.NO_CONTENT);
    }

}
