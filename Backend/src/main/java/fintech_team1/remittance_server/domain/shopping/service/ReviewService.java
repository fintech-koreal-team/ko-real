package fintech_team1.remittance_server.domain.shopping.service;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.entity.Review;
import fintech_team1.remittance_server.domain.shopping.entity.ReviewReaction;
import fintech_team1.remittance_server.domain.shopping.entity.enums.ReactionType;
import fintech_team1.remittance_server.domain.shopping.repository.ProductRepository;
import fintech_team1.remittance_server.domain.shopping.repository.ReviewReactionRepository;
import fintech_team1.remittance_server.domain.shopping.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewReactionRepository reviewReactionRepository;

    public List<Review> getReviewsForProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

        return reviewRepository.findByProduct(product);
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
    }

    public Review addReview(Member member, Long productId, Integer rating, String comment){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

        Review review = new Review();
        review.setMember(member);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Transactional
    public void reactToReview(Long reviewId, Member member, ReactionType reactionType) {
        Review review = getReviewById(reviewId);

        Optional<ReviewReaction> existingReaction = reviewReactionRepository.findByReviewAndMember(review, member);

        if (existingReaction.isPresent()) {
            ReviewReaction reaction = existingReaction.get();
            if (reaction.getReactionType() == reactionType) {
                // 현재 반응과 동일하면 반응 해제
                reviewReactionRepository.delete(reaction);
            } else {
                // 다른 반응으로 변경
                reaction.setReactionType(reactionType);
                reviewReactionRepository.save(reaction);
            }
        } else {
            // 새로운 반응 추가
            ReviewReaction reaction = new ReviewReaction();
            reaction.setReview(review);
            reaction.setMember(member);
            reaction.setReactionType(reactionType);
            reviewReactionRepository.save(reaction);
        }
    }
}
