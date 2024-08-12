package fintech_team1.remittance_server.domain.shopping.service;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.shopping.dto.Request.CheckoutOrderRequest;
import fintech_team1.remittance_server.domain.shopping.dto.Request.ItemRequest;
import fintech_team1.remittance_server.domain.shopping.entity.Address;
import fintech_team1.remittance_server.domain.shopping.entity.Order;
import fintech_team1.remittance_server.domain.shopping.entity.OrderItem;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.entity.enums.OrderStatus;
import fintech_team1.remittance_server.domain.shopping.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final MemberRepository memberRepository;
    private final CartService cartService;
    private final AddressService addressService;

    public List<Order> getOrdersForUser(Member member) {
        return orderRepository.findByMember(member);
    }

    @Transactional
    public Order createOrder(Member member, List<ItemRequest> orderItems){
        // 회원 검증
        Member existingMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("해당 사용자는 없는 사용자입니다."));

        // 기본 배송지 조회
        Address defaultAddress = addressService.getDefaultAddress(member);

        // 주문 객체 생성
        Order order = new Order();
        order.setMember(existingMember);
        order.setDefaultAddress(defaultAddress); // 기본 배송지가 없으면 null로 설정
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);  // 주문 상태를 CREATED로 설정
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 주문 항목 리스트
        List<OrderItem> orderItemList = new ArrayList<>();
        for (ItemRequest item : orderItems) {

            // 상품 정보 로깅
            log.info("Product ID: " + item.getProductId());
            log.info("Quantity: " + item.getQuantity());

            // 상품 조회
            Product product = productService.getProductById(item.getProductId());

            // 가격 계산
            BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemPrice);

            // 주문 항목 생성
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setPrice(itemPrice);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);

            orderItemList.add(orderItem);
        }
        log.info("totalAmount: " + totalAmount);

        // 주문 정보 설정
        order.setTotalAmount(totalAmount);
        order.setItems(orderItemList);
        log.info("orderItemList: " + orderItemList);

        // 장바구니에서 주문된 상품 제거
        cartService.removeAllFromCart(member, orderItems);

        // 주문 저장
        return orderRepository.save(order);
    }

    @Transactional
    public Order checkoutOrder(Long orderId, CheckoutOrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        order.setStatus(OrderStatus.PAYMENT_COMPLETED);
        order.setContactEmail(request.getContactEmail());
//        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPaymentMethod(request.getPaymentMethod());

        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Member member, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));
        if (!order.getMember().equals(member)) {
            throw new SecurityException("취소 권한이 없습니다.");
        }

        updateOrderStatus(orderId, OrderStatus.CANCELED);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        // 상태 업데이트
        order.setStatus(status);
        orderRepository.save(order);
    }

}
