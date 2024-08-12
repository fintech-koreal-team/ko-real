package fintech_team1.remittance_server.domain.remittance.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/currency")
@Tag(name = "환율 컨트롤러")
public class CurrencyController {
    private final CurrencyService currencyService;
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rate")
    @Operation(summary = "환율 정보 가져오기")
    public ResponseEntity<ApiResponse> getExchangeRate(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency){
        BigDecimal exchangeRange =  currencyService.getExchangeRate(fromCurrency, toCurrency);
        ApiResponse response = new ApiResponse("200", "환율", exchangeRange);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/convert")
    @Operation(summary = "통화 단위 변환하기")
    public ResponseEntity<ApiResponse> convertCurrency(
            @RequestParam BigDecimal amount,
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency) {
        BigDecimal convertedAmount = currencyService.convert(amount, fromCurrency, toCurrency);
        ApiResponse response = new ApiResponse("200", "변환된 금액", String.format("%.2f", convertedAmount));
        return ResponseEntity.ok(response);
    }
}
