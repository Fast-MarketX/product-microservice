package fast.market.product_microservice.kafka.consumer;

import fast.market.product_microservice.dto.ProductDto;
import fast.market.product_microservice.kafka.producer.ProductKafkaProducer;
import fast.market.product_microservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductKafkaConsumer {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductKafkaProducer productKafkaProducer;

    @KafkaListener(topics = "product-quantity-topic", groupId = "product-service-group")
    public void listenProductQuantities(Map<Long, Integer> productQuantities){
        List<ProductDto> products = mapToProductDtoList(productQuantities);

        productKafkaProducer.sendProductListToStorage(products);
    }

    private List<ProductDto> mapToProductDtoList(Map<Long, Integer> productQuantities){
        List<ProductDto> productList = new ArrayList<>();
        productQuantities.forEach((productId, quantity) -> {
            ProductDto productDto = productService.getProductById(productId);
            productList.add(productDto);
        });
        return productList;
    }
}
