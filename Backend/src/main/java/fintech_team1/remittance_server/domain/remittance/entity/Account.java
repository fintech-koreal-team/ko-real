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
@Schema(description = "계좌 모델")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    @ColumnDefault("0")
    private BigDecimal balance;

    @Column(nullable = false)
    private Boolean isDefault = false; // 기본 결제 계좌 여부

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 송금자 계좌로 보낸 송금 내역
    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transfer> sentTransfers = new ArrayList<>();

    // 수취인 계좌로 받은 송금 내역
    @OneToMany(mappedBy = "receiverAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transfer> receivedTransfers = new ArrayList<>();
}
