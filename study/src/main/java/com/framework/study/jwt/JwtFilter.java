package com.framework.study.jwt;

import com.framework.study.domain.Member;
import com.framework.study.dto.CustomMemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {
//        리퀘스트 당 jwt 검증하는 필터

    private  final JWTUtil jwtUtil;

    public JwtFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        리퀘스트 헤더 Authorization 찾음
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
//            jwt가 없는 경우에도 요청을 정상적으로 진행시키기 위해 호출하는 것
            filterChain.doFilter(request, response);

            return;
        }

//        토큰 추출
        String token = authorization.split(" ")[1];

//        토큰 만료시간 확인
        if(jwtUtil.isExpired(token)){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "만료된 토큰입니다.");
            return;
        }

        String username = jwtUtil.getUsername(token);

        Member member = new Member(username, "tmpPassword");

        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
