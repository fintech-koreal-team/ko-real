package fintech_team1.remittance_server.domain.shopping.service;


import fintech_team1.remittance_server.domain.shopping.entity.Brand;
import fintech_team1.remittance_server.domain.shopping.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandByName(String name) {
        return brandRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("브랜드를 찾을 수 없습니다."));
    }

    public Brand createBrand(String brandName, String imgUrl) {
        // 이름이 중복되지 않는지 확인
        if (brandRepository.findByName(brandName).isPresent()) {
            throw new RuntimeException("이미 존재하는 브랜드입니다.");
        }

        // 새로운 브랜드 객체 생성
        Brand brand = new Brand();
        brand.setName(brandName);
        brand.setImgUrl(imgUrl);

        // 브랜드 저장
        return brandRepository.save(brand);
    }

    public boolean deleteBrand(Long id) {
        if (brandRepository.existsById(id)) {
            brandRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Brand> getTop10Brands() {
        return brandRepository.findTop10ByOrderByPopularityDesc();
    }
}
