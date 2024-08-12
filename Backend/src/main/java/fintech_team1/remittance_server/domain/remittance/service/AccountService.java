package fintech_team1.remittance_server.domain.remittance.service;

import fintech_team1.remittance_server.domain.remittance.dto.Request.CreateAccountRequest;
import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.entity.Transfer;
import fintech_team1.remittance_server.domain.remittance.repository.AccountRepository;
import fintech_team1.remittance_server.domain.remittance.repository.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

// 계좌 서비스
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;
    private static final SecureRandom random = new SecureRandom();

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));
    }

    public Account createAccount(CreateAccountRequest accountRequest) {
        Account account = new Account();

        account.setAccountNumber(generateRandomAccountNumber());
        account.setAccountName(accountRequest.getAccountName());
        account.setBalance(BigDecimal.valueOf(0));
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
        } else {
            throw new RuntimeException("계좌를 찾을 수 없습니다.");
        }
    }

    public void updateAccountBalance(Account account, BigDecimal newBalance) {
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private static String generateRandomAccountNumber(){
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    public void recordTransaction(Account sender, Account receiver, BigDecimal amount, BigDecimal convertedAmount) {
        Transfer transfer = new Transfer();
        transfer.setSenderAccount(sender);
        transfer.setReceiverAccount(receiver);
        transfer.setAmount(amount);
        transfer.setConvertedAmount(convertedAmount);
        transfer.setSentDate(LocalDateTime.now());
        transfer.setReceivedDate(LocalDateTime.now());

        // 송금 내역 저장
        transferRepository.save(transfer);

        // 계좌의 송금 내역 업데이트
        sender.getSentTransfers().add(transfer);
        receiver.getReceivedTransfers().add(transfer);

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }
}
