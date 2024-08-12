package fintech_team1.remittance_server.domain.remittance.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Schema(description = "카드 모델")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private String cardExpiryDate; // MM/YY 형식

    @Column(nullable = false)
    private String cardCVV;

    @Column(nullable = false)
    private Boolean isDefault = false; // 기본 결제 카드 여부

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
