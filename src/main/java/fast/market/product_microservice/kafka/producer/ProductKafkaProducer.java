package fast.market.product_microservice.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fast.market.product_microservice.events.ProductEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProductKafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "product-events";

    public void sendProductEvent(ProductEvent event){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            String jsonEvent = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, jsonEvent);
        }catch (JsonProcessingException e){
            e.printStackTrace(); // log later
        }
    }
}
