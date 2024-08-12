package fintech_team1.remittance_server.global.security;

import fintech_team1.remittance_server.global.jwt.JWTUtil;
import fintech_team1.remittance_server.global.jwt.entity.RefreshEntity;
import fintech_team1.remittance_server.global.jwt.repository.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 클라이언트 요청에서 email, password 추출
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 요청의 email 또는 password가 없을 경우
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().write("Email and password must not be empty");
            } catch (IOException e) {
                throw new RuntimeException("Error sending response", e);
            }
            return null;
        }

        System.out.println("LoginFilter now");
        System.out.println("Attempting authentication with email: " + email);
        System.out.println("Attempting authentication with password: " + password);

        // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        // token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공 시 실행하는 메소드 (access JWT, refresh JWT 발급하기)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        // 유저 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 2592000000L); // 10분
        String refresh = jwtUtil.createJwt("refresh", username, role, 2592000000L); // 30일

        // Refresh 토큰 저장
        addRefreshEntity(username, refresh, 2592000000L); // 30일

        // 응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
        //
        Cookie accessToken = createCookie("AccessToken", access);
        Cookie refreshToken = createCookie("RefreshToken", refresh);
        cookieSetup(accessToken, response);
        cookieSetup(refreshToken, response);
    }

    private void cookieSetup(Cookie cookie, HttpServletResponse response) {
        cookie.setPath("/");
        cookie.setHttpOnly(false); //나중에 공부할것
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    // 로그인 실패 시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    // 쿠키 생성 메소드
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }

    // refreshToken 저장 메서드
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}