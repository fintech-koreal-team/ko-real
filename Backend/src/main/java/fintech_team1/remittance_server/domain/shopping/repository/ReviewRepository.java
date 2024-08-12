package fintech_team1.remittance_server.domain.shopping.repository;

import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}
