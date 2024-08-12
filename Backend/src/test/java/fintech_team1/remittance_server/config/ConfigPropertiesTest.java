package fintech_team1.remittance_server.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ConfigPropertiesTest {
    @Value("${api.currency.url}")
    private String apiUrl;

    @Value("${api.currency.access-key}")
    private String accessKey;

    @Test
    @DisplayName("애플리케이션 설정 파일의 속성이 올바르게 로드되는지 검증")
    public void testConfigProperties() {
        assertThat(apiUrl).isNotNull();
        assertThat(accessKey).isNotNull();
    }
}