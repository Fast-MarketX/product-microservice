package fast.market.product_microservice.exception.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoryExceptionHandler{
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(categoryNotFoundException.getMessage());
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Object> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException categoryAlreadyExistsException){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(categoryAlreadyExistsException.getMessage());
    }

    @ExceptionHandler(CategoryDuplicationException.class)
    public ResponseEntity<Object> handleCategoryDuplicationException(CategoryDuplicationException categoryDuplicationException){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(categoryDuplicationException.getMessage());
    }
}
