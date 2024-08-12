package fintech_team1.remittance_server.domain.remittance.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAccountRequest {
    private String accountNumber;
}