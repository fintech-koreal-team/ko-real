package fintech_team1.remittance_server.domain.remittance.repository;

import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.entity.Card;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByMember(Member member);
    Optional<Card> findByCardNumber(String accountNumber);
    Optional<Card> findByMemberAndIsDefault(Member member, Boolean isDefault);
}