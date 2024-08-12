package fintech_team1.remittance_server.domain.shopping.dto.Response;

import fintech_team1.remittance_server.domain.shopping.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartResponse {
    private Long id;
    private Product product;
    private Integer quantity;
}