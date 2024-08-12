package fintech_team1.remittance_server.domain.shopping.repository;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Wishlist findByMemberAndProduct(Member member, Product product);
    boolean existsByMemberAndProduct(Member member, Product product);
    List<Wishlist> findByMember(Member member);
}