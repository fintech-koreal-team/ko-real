package fintech_team1.remittance_server.domain.remittance.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class CacheableService {
    @Value("${api.currency.url}")
    private String apiUrl;

    @Value("${api.currency.access-key}")
    private String accessKey;

    private final WebClient webClient;

    @Autowired
    public CacheableService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    private URI buildUri(String fromCurrency, String toCurrency) {
        return UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("access_key", accessKey)
                .queryParam("source", fromCurrency)
                .queryParam("currencies", toCurrency)
                .build()
                .toUri();
    }

    // 외부 API 호출을 통해 환율 정보 가져오는 메서드 (캐싱 적용)
    @Cacheable(value = "exchangeRates", key = "#fromCurrency + '-' + #toCurrency")
    public String getExchangeRates(String fromCurrency, String toCurrency) {

        URI uri = buildUri(fromCurrency, toCurrency);
        log.info("Request URL: {}", uri);

        String response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 동기식으로 결과를 기다림

        log.info("API Response: {}", response);
        return response;
    }
}
