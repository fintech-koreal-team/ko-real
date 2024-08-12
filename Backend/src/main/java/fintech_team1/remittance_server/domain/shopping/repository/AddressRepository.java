package fintech_team1.remittance_server.domain.shopping.repository;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByMember(Member member);
    Optional<Address> findByMemberAndIsDefault(Member member, Boolean isDefault);
}