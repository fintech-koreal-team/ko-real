package fintech_team1.remittance_server.domain.remittance.service;

import fintech_team1.remittance_server.domain.remittance.dto.Request.TransferRequest;
import fintech_team1.remittance_server.domain.remittance.dto.Response.EstimateResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Account;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 송금 서비스
@Service
@RequiredArgsConstructor
@Transactional
public class TransferService {
    private final AccountService accountService;
    private final CurrencyService currencyService;

    public EstimateResponse generateEstimate(BigDecimal amount, String fromCurrency, String toCurrency, BigDecimal exchangeRate) {
        BigDecimal convertedAmount = currencyService.convert(amount, fromCurrency, toCurrency);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(30);

        return new EstimateResponse(amount, convertedAmount, fromCurrency, toCurrency, exchangeRate, expiryTime);
    }

    @Transactional
    public void processTransaction(TransferRequest transferRequest) {
        Account senderAccount = accountService.getAccount(transferRequest.getSenderAccountId());
        Account receiverAccount = accountService.getAccount(transferRequest.getReceiverAccountId());

        BigDecimal amount = transferRequest.getOriginalAmount();
        BigDecimal convertedAmount = transferRequest.getConvertedAmount();

        if (senderAccount.getBalance().compareTo(transferRequest.getOriginalAmount()) < 0) {
            throw new RuntimeException("송금자의 잔액이 부족합니다.");
        }

        // 계좌의 잔액 업데이트
        BigDecimal senderNewBalance = senderAccount.getBalance().subtract(amount);
        BigDecimal receiverNewBalance = receiverAccount.getBalance().add(convertedAmount);
        accountService.updateAccountBalance(senderAccount, senderNewBalance);
        accountService.updateAccountBalance(receiverAccount, receiverNewBalance);

        // 거래 기록 저장
        accountService.recordTransaction(senderAccount, receiverAccount, amount, convertedAmount);
    }
}
