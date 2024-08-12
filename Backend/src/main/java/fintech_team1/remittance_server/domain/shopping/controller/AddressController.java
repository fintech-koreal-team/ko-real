package fintech_team1.remittance_server.domain.shopping.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.remittance.repository.MemberRepository;
import fintech_team1.remittance_server.domain.shopping.dto.Request.AddressRequest;
import fintech_team1.remittance_server.domain.shopping.entity.Address;
import fintech_team1.remittance_server.domain.shopping.service.AddressService;
import fintech_team1.remittance_server.global.security.dto.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
@Tag(name = "배송지 컨트롤러")
public class AddressController {
    private final AddressService addressService;
    private final MemberRepository memberRepository;

    @GetMapping("{id}")
    @Operation(summary = "배송지 조회")
    public ResponseEntity<ApiResponse> getAccount(@PathVariable Long id) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Address address = addressService.getAddress(member, id);
        return ResponseEntity.ok(new ApiResponse("200", "배송지 조회", address));
    }


    @PostMapping
    @Operation(summary = "배송지 등록")
    public ResponseEntity<ApiResponse> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Address createdAddress = addressService.registerAddress(member, addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("201", "배송지를 등록했습니다.", createdAddress));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "배송지 삭제")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Long id) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        addressService.deleteAddress(member, id);
        return ResponseEntity.ok(new ApiResponse("204", "배송지가 삭제되었습니다.", ""));
    }

    @GetMapping("/default")
    @Operation(summary = "사용자 기본 배송지 조회")
    public ResponseEntity<ApiResponse> getDefaultAddress() {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        Address defaultAddress = addressService.getDefaultAddress(member);
        return ResponseEntity.ok(new ApiResponse("200", "사용자 기본 배송지", defaultAddress));
    }

    @PutMapping("/default/{newDefaultAddressId}")
    @Operation(summary = "사용자 기본 배송지 변경")
    public ResponseEntity<ApiResponse> changeDefaultAddress(
            @PathVariable Long newDefaultAddressId) {
        Member member = getAuthenticatedMemberV2(getAuthenticatedMember().getUserid());
        addressService.changeDefaultAddress(member, newDefaultAddressId);
        return ResponseEntity.ok(new ApiResponse("200", "기본 배송지가 성공적으로 변경되었습니다.", ""));
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
