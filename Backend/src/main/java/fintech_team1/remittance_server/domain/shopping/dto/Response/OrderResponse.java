package fintech_team1.remittance_server.domain.shopping.dto.Response;


import fintech_team1.remittance_server.domain.shopping.entity.Address;
import fintech_team1.remittance_server.domain.shopping.entity.OrderItem;
import fintech_team1.remittance_server.domain.shopping.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private List<OrderItem> items;

    private String contactEmail;
    private Address defaultAddress;
}