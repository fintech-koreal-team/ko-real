package fintech_team1.remittance_server.domain.shopping.controller;

import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.domain.shopping.entity.Brand;
import fintech_team1.remittance_server.domain.shopping.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
@Tag(name = "브랜드 컨트롤러")
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    @Operation(summary = "브랜드 조회")
    public ResponseEntity<ApiResponse> getBrands() {
        List<Brand> brandList = brandService.getAllBrands();
        return ResponseEntity.status(201).body(new ApiResponse("201", "브랜드 조회", brandList));
    }

    @PostMapping
    @Operation(summary = "브랜드 등록")
    public ResponseEntity<ApiResponse> createBrand(@RequestParam String brandName, String imgUrl) {
        Brand createdBrand = brandService.createBrand(brandName, imgUrl);
        return ResponseEntity.status(201).body(new ApiResponse("201", "브랜드 등록 성공", createdBrand));
    }
}
