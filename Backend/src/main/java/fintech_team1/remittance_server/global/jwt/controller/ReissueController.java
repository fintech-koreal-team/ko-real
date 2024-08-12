package fintech_team1.remittance_server.global.jwt.controller;

import fintech_team1.remittance_server.global.jwt.service.ReissueService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "JWT 컨트롤러")
public class ReissueController {
    private final ReissueService reissueService;

    @PostMapping("/reissue")
    @Operation(summary = "jwt 재발급 메서드")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키로 부터 refresh token 가져오기
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return new ResponseEntity<>("Refresh token not found", HttpStatus.BAD_REQUEST);
        }

        try {
            // access and refresh tokens 재발급
            String tokenResult = reissueService.reissueAccessToken(refreshToken);
            String[] tokens = tokenResult.split(";");

            String newAccessToken = tokens[0];
            String newRefreshToken = tokens[1];

            response.setHeader("access", newAccessToken);
            response.addCookie(createCookie("refresh", newRefreshToken));

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("Refresh token expired", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }
    }

    // 쿠키 생성 메소드
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
