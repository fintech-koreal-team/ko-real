package fintech_team1.remittance_server.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RequiredArgsConstructor
@DisplayName("외부_환율_API_호출_캐싱_처리_테스트")
class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testCache() {
        // 첫 호출: 캐시에 값이 없으므로 캐시 미스 발생
        currencyService.getExchangeRate("USD", "EUR");

        // Redis 캐시가 생성되었는지 확인
        Cache cache = cacheManager.getCache("exchangeRates");
        assertThat(cache).isNotNull();

        // 두 번째 호출: 캐시에서 값을 가져와야 하므로 캐시 히트 발생
        currencyService.getExchangeRate("USD", "EUR");

        // 캐시에서 값을 가져왔는지 확인
        assertThat(cache.get("USD-EUR")).isNotNull();
    }

}