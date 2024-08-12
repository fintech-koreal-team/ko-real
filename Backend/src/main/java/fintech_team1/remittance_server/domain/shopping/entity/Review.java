package fintech_team1.remittance_server.domain.shopping.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.entity.enums.ReactionType;
import fintech_team1.remittance_server.global.exception.BadRequestException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating; // 별점 (1~5)

    @Column(nullable = false)
    private String comment; // 리뷰평

    @Column(nullable = false)
    private LocalDateTime createdAt; // 리뷰 작성 날짜

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReviewReaction> reactions;

    public void setRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new BadRequestException("별점은 1점에서 5점까지만 부여할 수 있습니다.");
        }
        this.rating = rating;
    }

    @Transient
    public long getUpvotes() {
        return reactions.stream().filter(r -> r.getReactionType() == ReactionType.LIKE).count();
    }

    @Transient
    public long getDownvotes() {
        return reactions.stream().filter(r -> r.getReactionType() == ReactionType.DISLIKE).count();
    }
}
