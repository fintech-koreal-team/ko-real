package fintech_team1.remittance_server.domain.remittance.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.dto.Request.TransferRequest;
import fintech_team1.remittance_server.domain.remittance.service.TransferService;
import fintech_team1.remittance_server.domain.remittance.dto.Response.EstimateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
@Tag(name = "송금 컨트롤러")
public class TransferController {
    private final TransferService transferService;
    @Autowired
    public TransferController(TransferService transactionService){
        this.transferService = transactionService;
    }

    @GetMapping("/estimate")
    @Operation(summary = "송금 요청 및 견적서 생성")
    public ResponseEntity<ApiResponse> getEstimate(
            @RequestParam BigDecimal amount,
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam BigDecimal exchangeRate) {

        EstimateResponse response = transferService.generateEstimate(amount, fromCurrency, toCurrency, exchangeRate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("200", "견적서가 발행되었습니다.", response));
    }

    @PostMapping("/transfer")
    @Operation(summary = "송금 처리하기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "송금 처리가 완료되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "송금 처리를 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "senderAccountId", description = "송금인 계좌 ID"),
            @Parameter(name = "receiverAccountId", description = "수신인 계좌 ID"),
            @Parameter(name = "originalAmount", description = "기존 금액"),
            @Parameter(name = "convertedAmount", description = "변환된 금액"),
            @Parameter(name = "fromCurrency", description = "기존 통화"),
            @Parameter(name = "toCurrency", description = "변환된 통화"),
            @Parameter(name = "exchangeRate", description = "환율")
    })
    public ResponseEntity<ApiResponse> processTransaction(@RequestBody TransferRequest transferRequest) {
        try {
            transferService.processTransaction(transferRequest);
            return ResponseEntity.ok().body(new ApiResponse("200", "송금 처리가 완료되었습니다.", ""));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("400", "송금 처리를 실패했습니다.", e.getMessage()));
        }
    }
}
