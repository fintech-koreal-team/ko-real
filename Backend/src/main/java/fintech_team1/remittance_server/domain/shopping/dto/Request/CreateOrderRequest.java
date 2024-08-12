package fintech_team1.remittance_server.domain.shopping.dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {
    private List<ItemRequest> orderItems;
}
