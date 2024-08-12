package fintech_team1.remittance_server.domain.shopping.service;

import fintech_team1.remittance_server.domain.remittance.entity.Account;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import fintech_team1.remittance_server.domain.shopping.dto.Request.AddressRequest;
import fintech_team1.remittance_server.domain.shopping.entity.Address;
import fintech_team1.remittance_server.domain.shopping.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address getAddress(Member member, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));

        if (address.getMember() == null || !address.getMember().equals(member)) {
            throw new RuntimeException("이 배송지는 사용자의 등록된 배송지가 아닙니다.");
        }

        return address;
    }

    public Address registerAddress(Member member, AddressRequest addressRequest) {

        // AddressRequest를 Address 엔티티로 변환
        Address address = new Address();
        address.setRecipientName(addressRequest.getRecipientName());
        address.setStreetAddress(addressRequest.getStreetAddress());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setZipCode(addressRequest.getZipCode());
        address.setCountry(addressRequest.getCountry());
        address.setMember(member);

        // 모든 배송지의 기본 설정을 해제
        List<Address> addresses = addressRepository.findByMember(member);
        addresses.forEach(existingAddress -> {
            existingAddress.setIsDefault(false);
            addressRepository.save(existingAddress);
        });

        // 새로 등록된 배송지를 기본 배송지로 설정
        address.setIsDefault(true);

        // 주소 저장
        return addressRepository.save(address);
    }

    public void deleteAddress(Member member, Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));
        if (address.getMember() == null || !address.getMember().equals(member)) {
            throw new RuntimeException("이 배송지는 사용자의 등록된 배송지가 아닙니다.");
        }

        addressRepository.deleteById(id);

        // 모든 배송지의 기본 설정을 해제하고, 새로 등록된 계좌를 기본 계좌로 설정
        setFirstAddressAsDefault(member);
    }

    public Address getDefaultAddress(Member member) {
        // 사용자의 기본 배송지 조회
        return addressRepository.findByMemberAndIsDefault(member, true)
                .orElse(null); // 기본 배송지가 없으면 null 반환
    }

    public void changeDefaultAddress(Member member, Long newDefaultAddressId) {
        // 현재 기본 배송지를 조회하고 기본 설정 해제
        Address currentDefaultAddress = addressRepository.findByMemberAndIsDefault(member, true)
                .orElseThrow(() -> new RuntimeException("기본 배송지를 찾을 수 없습니다."));

        if (currentDefaultAddress != null && !currentDefaultAddress.getId().equals(newDefaultAddressId)) {
            currentDefaultAddress.setIsDefault(false);
            addressRepository.save(currentDefaultAddress);
        }

        // 새로운 기본 배송지 설정
        Address newDefaultAddress = addressRepository.findById(newDefaultAddressId)
                .orElseThrow(() -> new RuntimeException("새로 설정할 기본 배송지를 찾을 수 없습니다."));

        // 새 배송지가 사용자의 배송지인지 확인
        if (newDefaultAddress.getMember() == null || !newDefaultAddress.getMember().equals(member)) {
            throw new RuntimeException("새로 설정할 기본 배송지가 사용자가 등록한 배송지가 아닙니다.");
        }

        newDefaultAddress.setIsDefault(true);
        addressRepository.save(newDefaultAddress);
    }

    private void setFirstAddressAsDefault(Member member) {
        // 사용자의 모든 배송지를 조회
        List<Address> addresses = addressRepository.findByMember(member);

        // 모든 배송지의 기본 설정을 해제
        addresses.forEach(account -> {
            account.setIsDefault(false);
            addressRepository.save(account);
        });

        // 첫 번째 배송지를 기본 배송지로 설정
        if (!addresses.isEmpty()) {
            Address firstAddress = addresses.get(0);
            firstAddress.setIsDefault(true);
            addressRepository.save(firstAddress);
        }
    }
}
