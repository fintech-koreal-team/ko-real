package fintech_team1.remittance_server.domain.shopping.dto.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewRequest {
    @NotNull(message = "별점을 입력해주세요.")
    @Min(value = 1, message = "별점은 1점에서 5점까지만 부여할 수 있습니다.")
    @Max(value = 5, message = "별점은 1점에서 5점까지만 부여할 수 있습니다.")
    private Integer rating; // 별점 (1~5)

    @NotBlank(message = "리뷰 평을 입력해주세요.")
    private String comment; // 리뷰평
}
