package com.framework.study.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.study.dto.CustomMemberDetails;
import com.framework.study.dto.MemberRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter (AuthenticationManager authenticationManager, JWTUtil jwtUtil){
//       jwtUtil 주입
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
//      url 설정 default가 /login
        setFilterProcessesUrl("/api/member/login");
    }

    //UsernamePassword
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MemberRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), MemberRequestDto.class);

            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();


            //스프링 시큐리티에서 username과 password를 검증할 토큰 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            //토큰을 AuthenticationManager를 통해 검증
            return authenticationManager.authenticate(authToken);
        } catch (IOException e){
            throw new RuntimeException("로그인 요청 json 파싱 실패");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException{
        // AuthenticationManager을 통해 검증 성공시, jwt 발급
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();
        String username = customMemberDetails.getUsername();

        String token = jwtUtil.createJwt(username, 60*60*1000L );

        Map<String, Object> body = new HashMap<>();
        body.put("msg", "로그인 성공");
        body.put("statusCode", HttpServletResponse.SC_OK);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException{
        // AuthenticationManager을 통해 검증 실패 시

        Map<String, Object> body = new HashMap<>();
        body.put("msg", "로그인 실패");
        body.put("statusCode", HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    }

}
