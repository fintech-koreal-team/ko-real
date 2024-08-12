package fintech_team1.remittance_server.domain.remittance.service;

import fintech_team1.remittance_server.domain.remittance.entity.Card;
import fintech_team1.remittance_server.domain.remittance.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private static final Random RANDOM = new Random();

    public Card createCard(String cardHolderName) {
        Card card = new Card();
        card.setCardNumber(generateRandomCardNumber());
        card.setCardHolderName(cardHolderName);
        card.setCardExpiryDate(generateCardExpiryDate()); // 유효기간 설정
        card.setCardCVV(generateRandomCVV());


        return cardRepository.save(card);
    }

    public Card getCard(Long cardId) {

        return cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다."));
    }

    public void deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다."));

        cardRepository.deleteById(cardId);
    }


    private String generateRandomCardNumber() {
        // 카드 번호를 랜덤으로 생성 (16자리)
        return String.format("%04d %04d %04d %04d",
                RANDOM.nextInt(10000), RANDOM.nextInt(10000),
                RANDOM.nextInt(10000), RANDOM.nextInt(10000));
    }

    private String generateRandomCVV() {
        // CVV를 랜덤으로 생성 (3자리)
        return String.format("%03d", RANDOM.nextInt(1000));
    }

    private String generateCardExpiryDate() {
        // 유효기간을 현재로부터 4년 후로 설정 (MM/YY 형식)
        LocalDate expiryDate = LocalDate.now().plusYears(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return expiryDate.format(formatter);
    }
}
