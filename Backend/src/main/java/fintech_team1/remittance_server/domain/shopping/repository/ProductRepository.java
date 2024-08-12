package fintech_team1.remittance_server.domain.shopping.repository;

import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.entity.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<Product> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);

    List<Product> findTop10ByOrderByPopularityDesc();
    List<Product> findTop10ByOrderByCreatedDateDesc();
    
    Page<Product> findByCategory(Category category, Pageable pageable);
}
