package fintech_team1.remittance_server.domain.shopping.service;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.shopping.dto.Request.ItemRequest;
import fintech_team1.remittance_server.domain.shopping.entity.Cart;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.repository.CartRepository;
import fintech_team1.remittance_server.domain.shopping.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public List<Cart> getCartForUser(Member member) {
        return cartRepository.findByMember(member);
    }

    public Cart addToCart(Member member, ItemRequest cartItem) {
        Objects.requireNonNull(member);
        Objects.requireNonNull(member.getId());
        Member existingMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("해당 사용자는 없는 사용자입니다."));

        Product product = productService.getProductById(cartItem.getProductId());

        Cart cart = new Cart();
        cart.setMember(existingMember);
        cart.setProduct(product);
        cart.setQuantity(cartItem.getQuantity());

        return cartRepository.save(cart);
    }

    public void removeFromCart(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

        Cart cart = cartRepository.findByMemberAndProduct(member, product);
        if (cart != null) {
            cartRepository.delete(cart);
        } else {
            throw new RuntimeException("장바구니에서 해당 상품을 찾을 수 없습니다.");
        }
    }

    public void removeAllFromCart(Member member, List<ItemRequest> orderItems) {
        Objects.requireNonNull(member);
        Objects.requireNonNull(orderItems);

        // 주어진 주문 항목 리스트를 순회하며 장바구니에서 제거
        for (ItemRequest item : orderItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

            Cart cart = cartRepository.findByMemberAndProduct(member, product);
            if (cart != null) {
                cartRepository.delete(cart);
            }
        }
    }

    public Integer updateItemQuantity(Member member, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));
        Cart cart = cartRepository.findByMemberAndProduct(member, product);

        if (cart != null) {
            // 수량 업데이트
            cart.setQuantity(quantity);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("장바구니 항목을 찾을 수 없습니다.");
        }
        return cart.getQuantity();
    }
}
