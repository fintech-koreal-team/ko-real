package fintech_team1.remittance_server.domain.remittance.repository;

import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByMember(Member member);
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByMemberAndIsDefault(Member member, Boolean isDefault);
}
