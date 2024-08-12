package fintech_team1.remittance_server.domain.shopping.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.shopping.dto.Response.WishlistResponse;
import fintech_team1.remittance_server.domain.shopping.entity.Wishlist;
import fintech_team1.remittance_server.domain.shopping.service.WishlistService;
import fintech_team1.remittance_server.global.security.dto.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
@Tag(name = "찜하기 컨트롤러")
public class WishlistController {
    private final WishlistService wishlistService;
    private final MemberRepository memberRepository;

    @GetMapping
    @Operation(summary = "찜하기 목록 조회")
    public ResponseEntity<ApiResponse> getWishlist(){

        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Objects.requireNonNull(wishlistService);
        List<Wishlist> wishlist = wishlistService.getWishlistForUser(member);
        List<WishlistResponse> wishlistResponses = wishlist.stream()
                .map(wish -> new WishlistResponse(wish.getId(), wish.getProduct()))
                .toList();

        return ResponseEntity.ok(new ApiResponse("200", "찜하기 목록 조회", wishlistResponses));
    }

    @PostMapping("/{productId}")
    @Operation(summary = "찜하기")
    public ResponseEntity<ApiResponse> addToWishlist(@PathVariable Long productId) {

        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Objects.requireNonNull(wishlistService);
        Wishlist wish = wishlistService.addToWishlist(member, productId);
        WishlistResponse wishlistResponse = new WishlistResponse(wish.getId(), wish.getProduct());

        return ResponseEntity.ok(new ApiResponse("200", "찜하기 성공", wishlistResponse));
    }


    @DeleteMapping("/{productId}")
    @Operation(summary = "찜하기 해제")
    public ResponseEntity<ApiResponse> removeFromWishlist(@PathVariable Long productId) {

        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Objects.requireNonNull(wishlistService);
        wishlistService.removeFromWishlist(member, productId);

        return ResponseEntity.ok(new ApiResponse("200", "찜하기 해제 완료", ""));
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
