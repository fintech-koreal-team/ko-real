package fintech_team1.remittance_server.domain.shopping.repository;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.entity.Cart;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMemberAndProduct(Member member, Product product);
    List<Cart> findByMember(Member member);


}
