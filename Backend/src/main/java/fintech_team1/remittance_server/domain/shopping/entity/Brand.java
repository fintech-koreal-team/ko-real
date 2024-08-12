package fintech_team1.remittance_server.domain.shopping.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Schema(description = "브랜드 모델")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer popularity = 0; // 인기도

    private String imgUrl;

    @OneToMany(mappedBy = "brand")
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();

    public void incrementPopularity() {
        this.popularity += 1;
    }
    public void updateImgUrl(String imgUrls) {
        this.imgUrl = imgUrls;
    }
}
