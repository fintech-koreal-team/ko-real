package fintech_team1.remittance_server.domain.remittance.entity.enums;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;
    MemberRole(String value){
        this.value = value;
    }
}