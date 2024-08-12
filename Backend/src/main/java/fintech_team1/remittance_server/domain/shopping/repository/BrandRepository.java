package fintech_team1.remittance_server.domain.shopping.repository;

import fintech_team1.remittance_server.domain.shopping.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
    List<Brand> findTop10ByOrderByPopularityDesc();
}
