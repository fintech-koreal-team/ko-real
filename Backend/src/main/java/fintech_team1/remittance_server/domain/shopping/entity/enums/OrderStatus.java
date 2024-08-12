package fintech_team1.remittance_server.domain.shopping.entity.enums;

public enum OrderStatus {
    CANCELED,             // 주문 취소됨
    CREATED,              // 주문 생성됨
    PAYMENT_COMPLETED,    // 결제 완료됨
    ON_DELIVERY,          // 배송 중
    DELIVERY_COMPLETED,   // 배송 완료됨
    PURCHASE_CONFIRMED    // 구매 확정됨
}