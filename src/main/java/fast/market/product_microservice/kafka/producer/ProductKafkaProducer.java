package fast.market.product_microservice.kafka.producer;

import fast.market.product_microservice.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductKafkaProducer {
    private KafkaTemplate<String, List<ProductDto>> kafkaTemplate;

    public void sendProductListToStorage(List<ProductDto> productList){
        kafkaTemplate.send("product-list-topic", productList);
    }
}
