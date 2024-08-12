package fintech_team1.remittance_server.domain.shopping.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content; // 현재 페이지의 데이터
    private int totalPages; // 총 페이지 수
    private long totalElements; // 총 데이터 수
}