package fast.market.product_microservice.repository;

import fast.market.product_microservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByProductName(String productName);

    @Query("SELECT p from Product p JOIN FETCH p.categories WHERE p.productId = :productId")
    Optional<Product> findByIdWithCategories(Long productId);
}