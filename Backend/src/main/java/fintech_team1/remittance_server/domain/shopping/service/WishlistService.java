package fintech_team1.remittance_server.domain.shopping.service;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.entity.Wishlist;
import fintech_team1.remittance_server.domain.shopping.repository.WishlistRepository;
import fintech_team1.remittance_server.global.exception.ProductAlreadyInWishlistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductService productService;

    public List<Wishlist> getWishlistForUser(Member member) {
        return wishlistRepository.findByMember(member);
    }

    public Wishlist addToWishlist(Member member, Long productId) {
        Product product = productService.getProductById(productId);

        boolean alreadyInWishlist = wishlistRepository.existsByMemberAndProduct(member, product);
        if (alreadyInWishlist) {
            throw new ProductAlreadyInWishlistException("이미 찜이 되어있는 상품입니다.");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setMember(member);
        wishlist.setProduct(product);
        return wishlistRepository.save(wishlist);
    }

    public void
    removeFromWishlist(Member member, Long productId) {
        Product product = productService.getProductById(productId);
        Wishlist wishlist = wishlistRepository.findByMemberAndProduct(member, product);
        if (wishlist != null) {
            wishlistRepository.delete(wishlist);
        }
    }
}
