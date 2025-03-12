package com.framework.study.service;

import com.framework.study.domain.Member;
import com.framework.study.dto.MemberRequestDto;
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

    public void memberResister(MemberRequestDto memberRequestDto){
        String username = memberRequestDto.getUsername();
        String password = memberRequestDto.getPassword();

        //유저명 유효성 체크
        if(username == null || !USERNAME_PATTERN.matcher(username).matches()){
            log.error("유저명 유효성 검증 실패");
            return;
        }

        log.info("Received username : {}" , username);
        log.info("Received username : {}" , password);

        Boolean isExist = memberRepository.existsByUsername(username);

        if(isExist){
            log.info("Already username : {}", username);
            return ;
        }

        if (password == null){
            log.error("비밀번호 미입력");
            return;
        } else if(!PASSWORD_PATTERN.matcher(password).matches()) {
            log.error("비밀번호 유효성 검증 실패");
            return;
        }
        // 비밀번호 유효성 체크

        String newPassword = bCryptPasswordEncoder.encode(password);

        Member member = new Member(username, newPassword);

        memberRepository.save(member);

    }
}
