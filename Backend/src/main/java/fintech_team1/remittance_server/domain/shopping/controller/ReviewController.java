package fintech_team1.remittance_server.domain.shopping.controller;


import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.shopping.dto.Request.ReviewRequest;
import fintech_team1.remittance_server.domain.shopping.entity.Review;
import fintech_team1.remittance_server.domain.shopping.entity.enums.ReactionType;
import fintech_team1.remittance_server.domain.shopping.service.ReviewService;
import fintech_team1.remittance_server.global.security.dto.MemberDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Tag(name = "리뷰 컨트롤러")
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getReviews(@PathVariable Long productId){
        Objects.requireNonNull(reviewService);
        List<Review> reviewList = reviewService.getReviewsForProduct(productId);

        return ResponseEntity.ok(new ApiResponse("200", "리뷰 조회", reviewList));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse> addReview(
            @PathVariable Long productId,
            @Valid @RequestBody ReviewRequest reviewRequest){
        Objects.requireNonNull(reviewRequest);

        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Review review = reviewService.addReview(member, productId,
                                                reviewRequest.getRating(),
                                                reviewRequest.getComment());
        return ResponseEntity.ok(new ApiResponse("200", "리뷰 작성 성공", review));
    }

    @PostMapping("/{reviewId}/reaction")
    public ResponseEntity<ApiResponse> reactToReview(
            @PathVariable Long reviewId,
            @RequestParam ReactionType reactionType) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        reviewService.reactToReview(reviewId, member, reactionType);
        return ResponseEntity.ok(new ApiResponse("200", "반응이 업데이트 되었습니다.", ""));
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
