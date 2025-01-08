package fast.market.product_microservice.dto;

import fast.market.product_microservice.entity.Category;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long productId;

    private String productName;

    private String description;

    private double price;

    @OneToMany(mappedBy = "product")
    private Set<Category> categories;
}
