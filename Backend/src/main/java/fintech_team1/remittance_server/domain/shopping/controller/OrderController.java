package fintech_team1.remittance_server.domain.shopping.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.shopping.dto.Request.CheckoutOrderRequest;
import fintech_team1.remittance_server.domain.shopping.dto.Request.CreateOrderRequest;
import fintech_team1.remittance_server.domain.shopping.dto.Request.ItemRequest;
import fintech_team1.remittance_server.domain.shopping.dto.Response.OrderResponse;
import fintech_team1.remittance_server.domain.shopping.entity.Order;
import fintech_team1.remittance_server.domain.shopping.service.OrderService;
import fintech_team1.remittance_server.global.security.dto.MemberDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "주문 컨트롤러")
public class OrderController {
    private final OrderService orderService;
    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<ApiResponse> getOrders(){
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        List<Order> orderList = orderService.getOrdersForUser(member);
        List<OrderResponse> orderResponse = orderList.stream()
                .map(order -> new OrderResponse(order.getId(), order.getTotalAmount(), order.getStatus(), order.getOrderDate(), order.getItems(), order.getContactEmail(), order.getDefaultAddress()))
                .toList();

        return ResponseEntity.ok(new ApiResponse("200", "사용자 주문 내역 조회", orderResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> order(@RequestBody CreateOrderRequest createOrderRequest){
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        List<ItemRequest> orderItems = createOrderRequest.getOrderItems();
        Objects.requireNonNull(orderService);
        Order order = orderService.createOrder(member, orderItems);

        String contactEmail = member.getEmail();
        OrderResponse orderResponse = new OrderResponse(order.getId(), order.getTotalAmount(), order.getStatus(), order.getOrderDate(), order.getItems(), contactEmail, order.getDefaultAddress());

        return ResponseEntity.ok(new ApiResponse("200", "주문 생성 완료", orderResponse));

    }

    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<ApiResponse> checkoutOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody CheckoutOrderRequest checkoutOrderRequest) {
        Order order = orderService.checkoutOrder(orderId, checkoutOrderRequest);
        return ResponseEntity.ok(new ApiResponse("200", "주문서 작성이 완료되었습니다.", order));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        orderService.cancelOrder(member, orderId);
        return ResponseEntity.ok(new ApiResponse("200", "주문이 취소되었습니다.", ""));
    }

    // 인증된 사용자를 반환하는 메소드
    public Member getAuthenticatedMemberV2(String userId) {
        return memberRepository.getMemberByUserid(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
    }
    private Member getAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MemberDetails memberDetails) {
            // Member 객체 반환
            return memberDetails.getMember();
        } else {
            throw new RuntimeException("인증 정보가 없습니다.");
        }
    }
}
