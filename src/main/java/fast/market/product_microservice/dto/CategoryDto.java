package fast.market.product_microservice.dto;

import fast.market.product_microservice.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long categoryId;

    private String categoryName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
}
