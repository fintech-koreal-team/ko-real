package fintech_team1.remittance_server.domain.remittance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Schema(description = "송금 모델")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id")
    @JsonBackReference
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "receiver_account_id", nullable = false)
    private Account receiverAccount;

    @Column(nullable = false)
    private BigDecimal amount; // 송금액

    @Column(nullable = false)
    private BigDecimal convertedAmount; // 변환된 송금액

    @Column(nullable = false)
    private LocalDateTime sentDate;

    @Column(nullable = false)
    private LocalDateTime receivedDate;
}
