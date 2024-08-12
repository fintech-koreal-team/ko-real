package fintech_team1.remittance_server.domain.remittance.repository;

import fintech_team1.remittance_server.domain.remittance.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
