package fintech_team1.remittance_server.domain.remittance.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Request.MemberRequest;
import fintech_team1.remittance_server.domain.remittance.dto.Request.RegisterCardRequest;
import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.entity.Card;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.remittance.dto.Request.RegisterAccountRequest;
import fintech_team1.remittance_server.global.exception.DuplicateMemberException;
import fintech_team1.remittance_server.domain.remittance.service.MemberService;
import fintech_team1.remittance_server.global.security.dto.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "사용자 컨트롤러")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이미 사용하는 이메일입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이미 사용하는 아이디입니다", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "이메일"),
            @Parameter(name = "username", description = "이름"),
            @Parameter(name = "userid", description = "아이디"),
            @Parameter(name = "password", description = "비밀번호")
    })
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody MemberRequest memberRequest) {

        try {
            memberService.join(memberRequest);
            return ResponseEntity.ok(new ApiResponse("200", "회원가입되었습니다", ""));
        } catch (DuplicateMemberException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("400", e.getMessage(), ""));
        }
    }

    @PostMapping("/accounts")
    @Operation(summary = "사용자 계좌 등록")
    public ResponseEntity<ApiResponse> registerAccount(@Valid @RequestBody RegisterAccountRequest accountRequest) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Account createdAccount = memberService.registerAccount(member, accountRequest);
        return ResponseEntity.status(201).body(new ApiResponse("201", "계좌 등록 성공", createdAccount));
    }

    @GetMapping("/accounts/{id}")
    @Operation(summary = "사용자 계좌 정보 조회")
    public ResponseEntity<ApiResponse> getAccountInfo(@PathVariable Long id) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Account account = memberService.getAccount(member, id);
        ApiResponse response = new ApiResponse("200", "해당 계좌 정보 조회", account);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/accounts/{id}")
    @Operation(summary = "사용자 계좌 삭제")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Long id) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        memberService.deleteAccount(member, id);
        return ResponseEntity.ok(new ApiResponse("200", "계좌가 삭제되었습니다.", ""));
    }

    @GetMapping("/accounts/default")
    @Operation(summary = "사용자 기본 결제 계좌 조회")
    public ResponseEntity<ApiResponse> getDefaultAccount() {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Account defaultAccount = memberService.getDefaultAccount(member);
        return ResponseEntity.ok(new ApiResponse("200", "사용자 기본 계좌", defaultAccount));
    }

    @PutMapping("/accounts/default/{newDefaultAccountId}")
    @Operation(summary = "사용자 기본 결제 계좌 변경")
    public ResponseEntity<ApiResponse> changeDefaultAccount(
            @PathVariable Long newDefaultAccountId) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        memberService.changeDefaultAccount(member, newDefaultAccountId);
        return ResponseEntity.ok(new ApiResponse("200", "기본 결제 계좌가 성공적으로 변경되었습니다.", ""));
    }

    @PostMapping("/cards")
    @Operation(summary = "사용자 카드 등록")
    public ResponseEntity<ApiResponse> registerCard(@Valid @RequestBody RegisterCardRequest cardRequest) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Card createdCard = memberService.registerCard(member, cardRequest);
        return ResponseEntity.status(201).body(new ApiResponse("201", "카드 등록 성공", createdCard));
    }

    @GetMapping("/cards/{id}")
    @Operation(summary = "사용자 카드 정보 조회")
    public ResponseEntity<ApiResponse> getCardInfo(@PathVariable Long id) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Card card = memberService.getCard(member, id);
        ApiResponse response = new ApiResponse("200", "해당 카드 정보 조회", card);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cards/{id}")
    @Operation(summary = "사용자 카드 삭제")
    public ResponseEntity<ApiResponse> deleteCard(@PathVariable Long id) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        memberService.deleteCard(member, id);
        return ResponseEntity.ok(new ApiResponse("200", "카드가 삭제되었습니다.", ""));
    }

    @GetMapping("/cards/default")
    @Operation(summary = "사용자 기본 결제 카드 조회")
    public ResponseEntity<ApiResponse> getDefaultCard() {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Card defaultCard = memberService.getDefaultCard(member);
        return ResponseEntity.ok(new ApiResponse("200", "사용자 기본 키드", defaultCard));
    }

    @PutMapping("/cards/default/{newDefaultCardId}")
    @Operation(summary = "사용자 기본 결제 카드 변경")
    public ResponseEntity<ApiResponse> changeDefaultCard(
            @PathVariable Long newDefaultCardId) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        memberService.changeDefaultCard(member, newDefaultCardId);
        return ResponseEntity.ok(new ApiResponse("200", "기본 결제 카드가 성공적으로 변경되었습니다.", ""));
    }

    // 인증된 사용자를 반환하는 메소드
    public Member getAuthenticatedMemberV2(String userId) {
        return memberRepository.getMemberByUserid(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
    }
    private Member getAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MemberDetails memberDetails) {
            // Member 객체 반환
            return memberDetails.getMember();
        } else {
            throw new RuntimeException("인증 정보가 없습니다.");
        }
    }
}
