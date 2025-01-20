package fast.market.product_microservice.mapper;

import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto ProductToProductDto(Product product);
    Product ProductDtoToProduct(ProductDto productDto);
}