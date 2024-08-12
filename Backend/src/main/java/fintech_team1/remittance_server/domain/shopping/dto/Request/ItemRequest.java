package fintech_team1.remittance_server.domain.shopping.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {
    private Long productId;
    private Integer quantity;
}
