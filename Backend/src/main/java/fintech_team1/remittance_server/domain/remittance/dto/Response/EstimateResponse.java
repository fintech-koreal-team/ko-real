package fintech_team1.remittance_server.domain.remittance.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class EstimateResponse {
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal exchangeRate;
    private LocalDateTime expiryTime;
}
