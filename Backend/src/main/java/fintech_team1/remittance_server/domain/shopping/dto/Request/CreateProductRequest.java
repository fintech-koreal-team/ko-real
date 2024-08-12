package fintech_team1.remittance_server.domain.shopping.dto.Request;

import fintech_team1.remittance_server.domain.shopping.entity.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {
    @NotBlank(message = "상품 이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "상품코드를 입력해주세요.")
    private String productCode;

    @NotBlank(message = "브랜드를 입력해주세요.")
    private String brandName;

    @NotNull(message = "가격을 입력해주세요.")
    private BigDecimal price;

    @NotNull(message = "카테고리를 입력해주세요.")
    private Category category;

    @NotNull(message = "상품 설명을 입력해주세요.")
    private String description;

    @NotNull(message = "상품 성분을 입력해주세요.")
    private String ingredients;

}
