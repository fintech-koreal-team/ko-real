package fintech_team1.remittance_server.domain.shopping.repository;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.entity.Review;
import fintech_team1.remittance_server.domain.shopping.entity.ReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long> {
    Optional<ReviewReaction> findByReviewAndMember(Review review, Member member);
}