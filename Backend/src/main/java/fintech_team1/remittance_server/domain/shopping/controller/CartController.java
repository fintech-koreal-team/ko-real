package fintech_team1.remittance_server.domain.shopping.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.shopping.dto.Request.ItemRequest;
import fintech_team1.remittance_server.domain.shopping.dto.Response.CartResponse;
import fintech_team1.remittance_server.domain.shopping.entity.Cart;
import fintech_team1.remittance_server.domain.shopping.service.CartService;
import fintech_team1.remittance_server.global.security.dto.MemberDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
@Tag(name = "장바구니 컨트롤러")
public class CartController {
    private final CartService cartService;
    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<ApiResponse> getCart() {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Objects.requireNonNull(cartService);
        List<Cart> cartList = cartService.getCartForUser(member);
        List<CartResponse> cartResponseList = cartList.stream()
                .map(cart -> new CartResponse(cart.getId(), cart.getProduct(), cart.getQuantity()))
                .toList();

        return ResponseEntity.ok(new ApiResponse("200", "장바구니 조회", cartResponseList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addToCart(@RequestBody ItemRequest cartItem) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Objects.requireNonNull(cartService);

        log.info("Product ID: " + cartItem.getProductId());
        log.info("Quantity: " + cartItem.getQuantity());
        Cart cart = cartService.addToCart(member, cartItem);
        CartResponse cartResponse = new CartResponse(cart.getId(), cart.getProduct(), cart.getQuantity());

        return ResponseEntity.ok(new ApiResponse("200", "장바구니 담기 성공", cartResponse));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable Long productId) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Objects.requireNonNull(cartService);

        try {
            cartService.removeFromCart(member, productId);
            return ResponseEntity.ok(new ApiResponse("200", "장바구니 상품 제거 완료", ""));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("400", e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("500", "장바구니 상품 제거에 실패했습니다.", ""));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateCartItemQuantity(
            @RequestBody ItemRequest itemRequest) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());

        Integer quantity = cartService.updateItemQuantity(member, itemRequest.getProductId(), itemRequest.getQuantity());
        return ResponseEntity.ok(new ApiResponse("200", "장바구니 수량이 업데이트되었습니다.", quantity));
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
