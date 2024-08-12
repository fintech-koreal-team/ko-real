package fintech_team1.remittance_server.global.jwt.service;

import fintech_team1.remittance_server.global.jwt.repository.RefreshRepository;
import fintech_team1.remittance_server.global.jwt.JWTUtil;
import fintech_team1.remittance_server.global.jwt.entity.RefreshEntity;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public String reissueAccessToken(String refreshToken) throws ExpiredJwtException {
        if (jwtUtil.isExpired(refreshToken)) {
            throw new ExpiredJwtException(null, null, "Refresh token expired");
        }

        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
        if (!isExist) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        //새로운 JWT 생성
        String newAccess = jwtUtil.createJwt("access", username, role, 2592000000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 2592000000L); // 30일

        refreshRepository.deleteByRefresh(refreshToken);
        addRefreshEntity(username, newRefresh, 2592000000L); // 30일

        return newAccess + ";" + newRefresh;
    }

    // refreshToken 저장하는 메서드
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
