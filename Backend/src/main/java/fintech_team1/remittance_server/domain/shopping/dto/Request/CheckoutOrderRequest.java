package fintech_team1.remittance_server.domain.shopping.dto.Request;


import fintech_team1.remittance_server.domain.shopping.entity.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CheckoutOrderRequest {
    @NotNull(message = "연락처를 입력해주세요.")
    private String contactEmail;

    @NotNull(message = "배송지를 입력해주세요.")
    private String deliveryAddress;

    @NotNull(message = "결제 방식을 선택해해주세요.")
    private PaymentMethod paymentMethod;
}