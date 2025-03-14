package com.framework.study.service;

import com.framework.study.domain.Member;
import com.framework.study.dto.MemberRequestDto;
import com.framework.study.dto.MemberResponseDto;
import com.framework.study.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 정규식 패턴 상수 정의
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9]{4,10}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8,15}$");

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public MemberResponseDto memberResister(MemberRequestDto memberRequestDto){
        String username = memberRequestDto.getUsername();
        String password = memberRequestDto.getPassword();

        //유저명 유효성 체크
        if(username == null || !USERNAME_PATTERN.matcher(username).matches()){
            log.error("유저명 유효성 검증 실패");
            throw new RuntimeException("사용자명은 필수이며 4~10자 사이여야 합니다.");
        }

        log.info("Received username : {}" , username);
        log.info("Received username : {}" , password);

        boolean isExist = memberRepository.existsByUsername(username);

        if(isExist){
            log.info("Already username : {}", username);
            throw new RuntimeException("이미 사용 중인 사용자명입니다.");
        }

        if (password == null){
            log.error("비밀번호 미입력");
            throw new RuntimeException("비밀번호는 필수입니다.");
        } else if(!PASSWORD_PATTERN.matcher(password).matches()) {
            log.error("비밀번호 유효성 검증 실패");
            throw new RuntimeException("비밀번호는 8~15자 사이이며 영문, 숫자만 가능합니다.");
        }
        // 비밀번호 유효성 체크

        String newPassword = bCryptPasswordEncoder.encode(password);

        Member member = new Member(username, newPassword);
        Member savedMember = memberRepository.save(member);

        return new MemberResponseDto(savedMember);
    }
}
