package fintech_team1.remittance_server.domain.remittance.service;

import fintech_team1.remittance_server.domain.remittance.dto.Request.MemberRequest;
import fintech_team1.remittance_server.domain.remittance.dto.Request.RegisterCardRequest;
import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.entity.Card;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.entity.enums.MemberRole;
import fintech_team1.remittance_server.domain.remittance.repository.AccountRepository;
import fintech_team1.remittance_server.domain.remittance.repository.CardRepository;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.remittance.dto.Request.RegisterAccountRequest;
import fintech_team1.remittance_server.domain.shopping.entity.Address;
import fintech_team1.remittance_server.domain.shopping.repository.AddressRepository;
import fintech_team1.remittance_server.global.exception.DuplicateMemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


// 멤버 서비스
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void join(MemberRequest memberRequest) {
        if (memberRepository.existsByEmail(memberRequest.getEmail())) {
            throw new DuplicateMemberException("이미 사용하는 이메일입니다.");
        }
        if (memberRepository.existsByUserid(memberRequest.getUserid())) {
            throw new DuplicateMemberException("이미 사용 중인 아이디입니다.");
        }
        if (!memberRequest.getPassword().equals(memberRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .email(memberRequest.getEmail())
                .username(memberRequest.getUsername())
                .userid(memberRequest.getUserid())
                .password(passwordEncoder.encode(memberRequest.getPassword()))
                .role(MemberRole.USER.getValue())
                .build();

        memberRepository.save(member);
    }

    public Account getAccount(Member member, Long accountId) {
        // 계좌 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));

        // 계좌가 사용자의 계좌인지 확인
        if (account.getMember() == null || !account.getMember().equals(member)) {
            throw new RuntimeException("이 계좌는 사용자의 계좌가 아닙니다.");
        }

        return account;
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));
    }

    public Account registerAccount(Member member, RegisterAccountRequest accountRequest) {
        Account account = getAccountByNumber(accountRequest.getAccountNumber());

        if (account.getMember() == null) {
            account.setMember(member);

            // 모든 계좌의 기본 설정을 해제하고, 새로 등록된 계좌를 기본 계좌로 설정
            setFirstAccountAsDefault(member);
            return accountRepository.save(account);
        }
        throw new RuntimeException("계좌는 이미 등록되어 있습니다.");
    }

    public void deleteAccount(Member member, Long id) {
        // 계좌 조회
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));

        // 계좌가 해당 회원에 속하는지 확인
        if (account.getMember() == null || !account.getMember().equals(member)) {
            throw new RuntimeException("이 계좌는 사용자의 계좌가 아닙니다.");
        }

        // 계좌 삭제
        accountRepository.deleteById(id);

        // 모든 계좌의 기본 설정을 해제하고, 새로 등록된 계좌를 기본 계좌로 설정
        setFirstAccountAsDefault(member);
    }

    public Account getDefaultAccount(Member member) {
        // 사용자의 기본 계좌 조회
        return accountRepository.findByMemberAndIsDefault(member, true)
                .orElseThrow(() -> new RuntimeException("기본 결제 계좌를 찾을 수 없습니다."));
    }

    public void changeDefaultAccount(Member member, Long newDefaultAccountId) {
        // 현재 기본 결제 계좌를 조회하고 기본 설정 해제
        Account currentDefaultAccount = accountRepository.findByMemberAndIsDefault(member, true)
                .orElseThrow(() -> new RuntimeException("기본 결제 계좌를 찾을 수 없습니다."));

        if (currentDefaultAccount != null && !currentDefaultAccount.getId().equals(newDefaultAccountId)) {
            currentDefaultAccount.setIsDefault(false);
            accountRepository.save(currentDefaultAccount);
        }

        // 새로운 기본 결제 계좌 설정
        Account newDefaultAccount = accountRepository.findById(newDefaultAccountId)
                .orElseThrow(() -> new RuntimeException("새로 설정할 기본 결제 계좌를 찾을 수 없습니다."));

        // 새 계좌가 사용자의 계좌인지 확인
        if (newDefaultAccount.getMember() == null || !newDefaultAccount.getMember().equals(member)) {
            throw new RuntimeException("새로 설정할 기본 결제 계좌가 사용자의 계좌가 아닙니다.");
        }

        newDefaultAccount.setIsDefault(true);
        accountRepository.save(newDefaultAccount);
    }

    private void setFirstAccountAsDefault(Member member) {
        // 사용자의 모든 계좌를 조회
        List<Account> accounts = accountRepository.findByMember(member);

        // 모든 계좌의 기본 설정을 해제
        accounts.forEach(account -> {
            account.setIsDefault(false);
            accountRepository.save(account);
        });

        // 첫 번째 계좌를 기본 결제 계좌로 설정
        if (!accounts.isEmpty()) {
            Account firstAccount = accounts.get(0);
            firstAccount.setIsDefault(true);
            accountRepository.save(firstAccount);
        }
    }

    public Card getCard(Member member, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다."));

        // 사용자의 카드인지 확인
        if (card.getMember() == null || !card.getMember().equals(member)) {
            throw new RuntimeException("이 카드는 사용자의 카드가 아닙니다.");
        }

        return card;
    }

    public Card getCardByNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다."));
    }

    public Card registerCard(Member member, RegisterCardRequest cardRequest) {
        Card card = getCardByNumber(cardRequest.getCardNumber());

        if (card.getMember() == null) {
            card.setMember(member);

            // 모든 카드의 기본 설정을 해제하고, 새로 등록된 카드를 기본 카드로 설정
            setFirstCardAsDefault(member);
            return cardRepository.save(card);
        }
        throw new RuntimeException("카드는 이미 등록되어 있습니다.");
    }

    public void deleteCard(Member member, Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다."));

        if (card.getMember() == null || !card.getMember().equals(member)) {
            throw new RuntimeException("이 카드는 사용자의 카드가 아닙니다.");
        }

        cardRepository.deleteById(id);

        // 모든 계좌의 기본 설정을 해제하고, 새로 등록된 계좌를 기본 계좌로 설정
        setFirstCardAsDefault(member);
    }

    public Card getDefaultCard(Member member) {
        return cardRepository.findByMemberAndIsDefault(member, true)
                .orElseThrow(() -> new RuntimeException("기본 결제 카드를 찾을 수 없습니다."));
    }

    public void changeDefaultCard(Member member, Long newDefaultCardId) {
        Card currentDefaultCard = cardRepository.findByMemberAndIsDefault(member, true)
                .orElseThrow(() -> new RuntimeException("기본 결제 카드를 찾을 수 없습니다."));

        if (currentDefaultCard != null && !currentDefaultCard.getId().equals(newDefaultCardId)) {
            currentDefaultCard.setIsDefault(false);
            cardRepository.save(currentDefaultCard);
        }

        Card newDefaultCard= cardRepository.findById(newDefaultCardId)
                .orElseThrow(() -> new RuntimeException("새로 설정할 기본 결제 카드를 찾을 수 없습니다."));

        if (newDefaultCard.getMember() == null || !newDefaultCard.getMember().equals(member)) {
            throw new RuntimeException("새로 설정할 기본 결제 카드가 사용자의 카드가 아닙니다.");
        }

        newDefaultCard.setIsDefault(true);
        cardRepository.save(newDefaultCard);
    }

    private void setFirstCardAsDefault(Member member) {
        List<Card> cards = cardRepository.findByMember(member);

        // 모든 카드의 기본 설정을 해제
        cards.forEach(account -> {
            account.setIsDefault(false);
            cardRepository.save(account);
        });

        // 첫 번째 카드를 기본 결제 카드로 설정
        if (!cards.isEmpty()) {
            Card firstCard = cards.get(0);
            firstCard.setIsDefault(true);
            cardRepository.save(firstCard);
        }
    }
}
