package fintech_team1.remittance_server.global.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "api.currency")
@Getter
public class ConfigProperties {

    @NotEmpty
    private final String url;
    @NotEmpty
    private final String accessKey;

    public ConfigProperties(String url, String accessKey) {
        this.url = url;
        this.accessKey = accessKey;
    }
}
