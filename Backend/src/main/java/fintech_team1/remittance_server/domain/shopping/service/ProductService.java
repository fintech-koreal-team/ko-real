package fintech_team1.remittance_server.domain.shopping.service;

import fintech_team1.remittance_server.domain.shopping.entity.Brand;
import fintech_team1.remittance_server.domain.shopping.dto.Request.CreateProductRequest;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.entity.enums.Category;
import fintech_team1.remittance_server.domain.shopping.repository.BrandRepository;
import fintech_team1.remittance_server.domain.shopping.repository.ProductImageRepository;
import fintech_team1.remittance_server.domain.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductImageRepository productImageRepository;

    public Page<Product> getAllProducts(int page, int size) {
        // 페이징 처리
        Pageable pageable = PageRequest.of(page, size);
        List<String> imgUrls = productImageRepository.findByAllImgUrls();
        Page<Product> all = productRepository.findAll(pageable);

        List<Product> products = all.getContent();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            if (i < imgUrls.size()) {
                product.updateImgUrl(imgUrls.get(i));
            } else {
                product.updateImgUrl(null);
            }
        }
        return productRepository.findAll(pageable);
    }

    public Page<Product> getProductsByCategory(Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategory(category, pageable);
    }

    public Product getProductById(Long id) {
        // 상품 조회
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 상품은 존재하지 않는 상품입니다."));

        String imgUrls = productImageRepository.selectImgUrlByCode(product.getProductCode());
        product.updateImgUrl(imgUrls);

        // 브랜드 popularity 증가
        Brand brand = product.getBrand();
        brand.incrementPopularity();
        brandRepository.save(brand);

        // 상품 popularity 증가
        product.incrementPopularity();

        // 업데이트된 상품 저장
        return productRepository.save(product);
    }

    public Long createProduct(CreateProductRequest requestDto) {
        // 브랜드 조회
        Brand brand = brandRepository.findByName(requestDto.getBrandName())
                .orElseThrow(() -> new RuntimeException("브랜드를 찾을 수 없습니다."));

        // 상품 객체 생성
        Product product = new Product();
        product.setProductCode(requestDto.getProductCode());
        product.setName(requestDto.getName());
        product.setBrand(brand);
        product.setPrice(requestDto.getPrice());
        product.setDescription(requestDto.getDescription());
        product.setIngredients(requestDto.getIngredients());
        product.setCategory(requestDto.getCategory());

        // 엔티티 저장
        productRepository.save(product);

        // 생성된 상품 ID 반환
        return product.getId();
    }

    public Page<Product> searchProducts(String name, String description, int page, int size) {
        // 페이징 처리
        Pageable pageable = PageRequest.of(page, size);

        if (name != null && description != null) {
            return productRepository.findByNameContainingOrDescriptionContaining(name, description, pageable);
        } else if (name != null) {
            return productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (description != null) {
            return productRepository.findByDescriptionContainingIgnoreCase(description, pageable);
        }
        return productRepository.findAll(pageable);
    }

    public List<Product> getTop10PopularProducts() {
        return productRepository.findTop10ByOrderByPopularityDesc();
    }

    public List<Product> getTop10NewestProducts() {
        return productRepository.findTop10ByOrderByCreatedDateDesc();
    }
}
