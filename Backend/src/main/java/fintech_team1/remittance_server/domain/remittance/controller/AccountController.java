package fintech_team1.remittance_server.domain.remittance.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Request.CreateAccountRequest;
import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Tag(name = "계좌 컨트롤러")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("{id}")
    @Operation(summary = "계좌 조회")
    public ResponseEntity<ApiResponse> getAccount(@PathVariable Long id) {
        Account account = accountService.getAccount(id);
        return ResponseEntity.ok(new ApiResponse("200", "계좌 조회", account));
    }

    @PostMapping
    @Operation(summary = "계좌 생성")
    public ResponseEntity<ApiResponse> createAccount(@RequestBody CreateAccountRequest accountRequest) {
        Account newAccount = accountService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("201", "계좌를 생성했습니다.", newAccount));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "계좌 삭제")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(new ApiResponse("204", "계좌가 삭제되었습니다.", ""));
    }
}
