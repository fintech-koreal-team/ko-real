package fintech_team1.remittance_server.domain.shopping.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.entity.enums.OrderStatus;
import fintech_team1.remittance_server.domain.shopping.entity.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Schema(description = "구매내역 모델")
@Table(name = "order_entity") // 테이블 이름을 예약어가 아닌 다른 이름으로 변경
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;


    @Column(nullable = true)
    private String contactEmail; // 연락처 이메일

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address defaultAddress; // 기본 배송지 정보

    @Column(nullable = true)
    private PaymentMethod paymentMethod; // 결제 방법

    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();
}
