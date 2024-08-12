package fintech_team1.remittance_server.domain.remittance.controller;


import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Card;
import fintech_team1.remittance_server.domain.remittance.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCard(@RequestParam String cardHolderName) {
        Card card = cardService.createCard(cardHolderName);
        return ResponseEntity.ok(new ApiResponse("200", "카드 생성 완료", card));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCard(@PathVariable Long id) {
        Card card = cardService.getCard(id);
        return ResponseEntity.ok(new ApiResponse("200", "카드 조회", card));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok(new ApiResponse("204", "카드 삭제 완료", ""));
    }
}
