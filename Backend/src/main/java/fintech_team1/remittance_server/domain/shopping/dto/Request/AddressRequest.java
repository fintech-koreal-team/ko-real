package fintech_team1.remittance_server.domain.shopping.dto.Request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    @NotEmpty(message = "수취인 이름은 필수입니다.")
    private String recipientName;

    @NotEmpty(message = "주소는 필수입니다.")
    private String streetAddress;

    @NotEmpty(message = "도시는 필수입니다.")
    private String city;

    @NotEmpty(message = "주(State)는 필수입니다.")
    private String state;

    @NotEmpty(message = "우편번호는 필수입니다.")
    private String zipCode;

    @NotEmpty(message = "국가는 필수입니다.")
    private String country;
}
