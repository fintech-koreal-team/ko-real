package fintech_team1.remittance_server.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

// 외환 환전 서비스
@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyService {
    private final CacheableService cacheableService;

    // 특정 기준 통화에서 다른 통화로 금액을 변환하는 메서드
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        String jsonString = cacheableService.getExchangeRates(fromCurrency, toCurrency);
        JSONObject json = new JSONObject(jsonString);

        JSONObject quotes = json.optJSONObject("quotes");
        if (quotes == null) {
            log.error("응답 JSON에서 'quotes' 필드를 찾을 수 없습니다. 응답 JSON: {}", jsonString);
            throw new RuntimeException("'quotes' field not found in the response");
        }

        // 환율 추출
        String rateKey = fromCurrency + toCurrency;
        if (!quotes.has(rateKey)) {
            log.error("환율 데이터가 응답에서 찾을 수 없습니다. 요청된 통화: {}->{}. 응답 JSON: {}", fromCurrency, toCurrency, jsonString);
            throw new RuntimeException("Exchange rate not found for key: " + rateKey);
        }

        double rateDouble = quotes.getDouble(fromCurrency + toCurrency);
        BigDecimal rate = BigDecimal.valueOf(rateDouble);

        log.info("통화 변환 정보: from {} to {}", fromCurrency, toCurrency);
        log.info("환율: {} = {}", fromCurrency + toCurrency, rate);

        return rate;
    }

    // 특정 기준 통화에서 다른 통화로 금액을 변환하는 메서드
    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        BigDecimal rate = getExchangeRate(fromCurrency, toCurrency);
        BigDecimal convertedAmount = amount.multiply(rate);

        log.info("입력 금액: {} * 환율: {} = 변환된 금액: {}", amount, rate, convertedAmount);

        return convertedAmount;
    }
}
