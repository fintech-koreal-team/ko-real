package fintech_team1.remittance_server.domain.remittance.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    @Email
    @NotBlank
    private String email;
    private String username;
    private String userid;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword; // 비밀번호 확인란
}
