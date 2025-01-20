package fast.market.product_microservice.events;

import fast.market.product_microservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {
    private Long productId;
    private String productName;
    private Double productPrice;
    private ProductEventType eventType; // CREATED, UPDATED, DELETED

    public ProductEvent (Product product, ProductEventType eventType){
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productPrice = product.getPrice();
        this.eventType = eventType;
    }
}