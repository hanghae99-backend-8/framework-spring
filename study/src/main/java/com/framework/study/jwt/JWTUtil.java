package com.framework.study.jwt;

import com.framework.study.domain.Member;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret){
        //jwt key => 객체변수로 암호화
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

//    내부 메서드 구현
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public boolean isExpired(String token){
        //jwts 파싱 -> 시크릿키로 검증 -> 빌드타입 -> 토큰 검증 -> 토큰에서 페이로드 호출 -> 현재시간 값을 입력해서 만료일 검증
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    //jwt 발급
    public String createJwt(String username, Long expiredMS){

        long currentTime = System.currentTimeMillis();

        return Jwts.builder()
                .claim("username", username)    // 페이로드 들어감
                .issuedAt(new Date(currentTime)) // -> 발급 일자
                .expiration(new Date(currentTime + expiredMS)) // -> 만료일
                .signWith(secretKey)    // -> jwt signature
                .compact();
    }


}
