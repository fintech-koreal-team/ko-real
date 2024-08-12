package fintech_team1.remittance_server.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import fintech_team1.remittance_server.domain.remittance.dto.Response.ApiResponse;
import fintech_team1.remittance_server.global.security.dto.MemberDetails;
import fintech_team1.remittance_server.domain.remittance.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            // response body
            ApiResponse apiResponse = new ApiResponse("401", "Access token expired", "");

            // 응답 설정
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            // ApiResponse를 JSON으로 변환하여 응답 본문에 작성
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            return;
        }

        // 토큰이 access인지 확인 (발급 시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            // response body
            ApiResponse apiResponse = new ApiResponse("401", "Invalid access token", "");

            // 응답 설정
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            // ApiResponse를 JSON으로 변환하여 응답 본문에 작성
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            return;
        }

        // 토큰에서 username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        // userEntity를 생성하여 값 set
        Member member = Member.builder()
                .userid(username)
                .role(role)
                .password("temppassword")
                .build();

        // UserDetails에 회원 정보 객체 담기
        MemberDetails memberDetails = new MemberDetails(member);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터 진행
        filterChain.doFilter(request, response);
    }
}
