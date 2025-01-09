package fast.market.product_microservice.mapper;

import fast.market.product_microservice.dto.CategoryDto;
import fast.market.product_microservice.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto CategoryToCategoryDto(Category category);

    Category CategoryDtoToCategory(CategoryDto categoryDto);
}
