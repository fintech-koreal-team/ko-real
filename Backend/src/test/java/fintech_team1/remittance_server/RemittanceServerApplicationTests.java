package fintech_team1.remittance_server;

import fintech_team1.remittance_server.global.config.ConfigProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(ConfigProperties.class)
class RemittanceServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
