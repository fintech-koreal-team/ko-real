package fintech_team1.remittance_server.domain.shopping.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.shopping.dto.Request.CreateProductRequest;
import fintech_team1.remittance_server.domain.shopping.dto.Response.PagedResponse;
import fintech_team1.remittance_server.domain.shopping.entity.Brand;
import fintech_team1.remittance_server.domain.shopping.entity.Product;
import fintech_team1.remittance_server.domain.shopping.entity.enums.Category;
import fintech_team1.remittance_server.domain.shopping.service.BrandService;
import fintech_team1.remittance_server.domain.shopping.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "상품 컨트롤러")
public class ProductController {
    private final ProductService productService;
    private final BrandService brandService;

    @GetMapping
    @Operation(summary = "전체 상품 목록 조회")
    public ResponseEntity<ApiResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size){

        Page<Product> productPage = productService.getAllProducts(page, size);
        PagedResponse<Product> pagedResponse = new PagedResponse<>(
                productPage.getContent(), // 현재 페이지의 데이터
                productPage.getTotalPages(), // 총 페이지 수
                productPage.getTotalElements() // 총 데이터 수
        );

        return ResponseEntity.ok(new ApiResponse(
                "200",
                "전체 상품 목록 조회",
                pagedResponse
        ));
    }

    @GetMapping("/category")
    @Operation(summary = "카테고리 별 상품 목록 조회")
    public ResponseEntity<ApiResponse> getProductsByCategory(
            @RequestParam Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {

        Page<Product> productPage = productService.getProductsByCategory(category, page, size);
        PagedResponse<Product> pagedResponse = new PagedResponse<>(
                productPage.getContent(), // 현재 페이지의 데이터
                productPage.getTotalPages(), // 총 페이지 수
                productPage.getTotalElements() // 총 데이터 수
        );

        return ResponseEntity.ok(new ApiResponse(
                "200",
                "전체 상품 목록 조회",
                pagedResponse
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "상품 세부 정보 조회")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("200", "상품 세부 정보 조회", product));
    }

    @PostMapping
    @Operation(summary = "상품 등록하기")
    public ResponseEntity<ApiResponse> addProduct(
            @Valid @ModelAttribute CreateProductRequest requestDto) {

        // Product 객체 생성 및 값 설정
        Long productId = productService.createProduct(requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("200", "상품 등록하기", productId));
    }

    @GetMapping("/search")
    @Operation(summary = "상품 검색")
    public ResponseEntity<ApiResponse> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {

        Page<Product> productPage = productService.searchProducts(name, description, page, size);
        PagedResponse<Product> pagedResponse = new PagedResponse<>(
                productPage.getContent(), // 현재 페이지의 데이터
                productPage.getTotalPages(), // 총 페이지 수
                productPage.getTotalElements() // 총 데이터 수
        );

        ApiResponse response = new ApiResponse(
                "200",
                "상품 검색",
                pagedResponse
        );

        return ResponseEntity.ok(new ApiResponse(
                "200",
                "전체 상품 목록 조회",
                response
        ));
    }

    @GetMapping("/top-popular")
    @Operation(summary = "인기 상품 Top 10 조회")
    public ResponseEntity<ApiResponse> getTop10PopularProducts() {
        List<Product> top10PopularProducts = productService.getTop10PopularProducts();
        return ResponseEntity.ok(new ApiResponse(
                "200",
                "인기 상품 Top 10 조회",
                top10PopularProducts
        ));
    }

    @GetMapping("/top-newest")
    @Operation(summary = "최근 상품 Top 10 조회")
    public ResponseEntity<ApiResponse> getTop10NewestProducts() {
        List<Product> top10NewestProducts = productService.getTop10NewestProducts();
        return ResponseEntity.ok(new ApiResponse(
                "200",
                "최근 상품 Top 10 조회",
                top10NewestProducts
        ));
    }

    @GetMapping("/top-brand")
    @Operation(summary = "인기 브랜드 Top 10 조회")
    public ResponseEntity<ApiResponse> getTop10Brands() {
        List<Brand> top10Brands = brandService.getTop10Brands();
        return ResponseEntity.ok(new ApiResponse("200", "인기 브랜드 Top 10", top10Brands));
    }
}
