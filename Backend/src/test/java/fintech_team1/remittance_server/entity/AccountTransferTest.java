package fintech_team1.remittance_server.entity;

import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.entity.Transfer;
import fintech_team1.remittance_server.domain.remittance.repository.AccountRepository;
import fintech_team1.remittance_server.domain.remittance.repository.TransferRepository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("계좌_송금_엔티티_관계_테스트")
public class AccountTransferTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferRepository transferRepository;

    @Test
    @Transactional
    public void testAccountTransferRelations(){
        // 송금자 계좌 생성 및 설정
        Account sender = new Account();
        sender.setAccountNumber("1234567890");
        sender.setAccountName("오렌지");
        sender.setBalance(new BigDecimal("1000"));

        // 수취인 계좌 생성 및 설정
        Account receiver = new Account();
        receiver.setAccountNumber("0987654321");
        receiver.setAccountName("사과");
        receiver.setBalance(new BigDecimal("500"));

        // 계좌 저장
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // 송금 기록 생성 및 설정
        Transfer transfer = new Transfer();
        transfer.setSenderAccount(sender);
        transfer.setReceiverAccount(receiver);
        transfer.setAmount(new BigDecimal("2345"));
        transfer.setConvertedAmount(new BigDecimal("999"));
        transfer.setSentDate(LocalDateTime.now());
        transfer.setReceivedDate(LocalDateTime.now());

        // 양방향 연관관계 설정
        sender.getSentTransfers().add(transfer);
        receiver.getReceivedTransfers().add(transfer);

        // 송금 기록 저장
        transferRepository.save(transfer);

        // 계좌 저장 (연관관계 업데이트)
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // 계좌를 다시 조회하여 연관관계가 제대로 설정되었는지 확인
        Account savedSender = accountRepository.findById(sender.getId()).orElse(null);
        Account savedReceiver = accountRepository.findById(receiver.getId()).orElse(null);

        assertThat(savedSender).isNotNull();
        assertThat(savedReceiver).isNotNull();
        assertThat(savedSender.getSentTransfers()).hasSize(1);
        assertThat(savedReceiver.getReceivedTransfers()).hasSize(1);
    }
}
