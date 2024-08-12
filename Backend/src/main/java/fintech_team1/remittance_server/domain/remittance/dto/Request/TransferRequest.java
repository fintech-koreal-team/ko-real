package fintech_team1.remittance_server.domain.remittance.dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransferRequest {
    private Long senderAccountId;
    private Long receiverAccountId;
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private String fromCurrency;
    private String toCurrency;
    private double exchangeRate;
}
