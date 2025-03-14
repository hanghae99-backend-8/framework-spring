package com.framework.study.controller;

import com.framework.study.domain.Member;
import com.framework.study.dto.MemberRequestDto;
import com.framework.study.dto.MemberResponseDto;
import com.framework.study.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/member")
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("")
    public String getMembermain() {
        return "member";
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> memberRegister(@RequestBody MemberRequestDto memberRequestDto){

        try {
            MemberResponseDto memberResponseDto = memberService.memberResister(memberRequestDto);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("statusCode", HttpStatus.OK);
            responseBody.put("msg", "회원가입 성공");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", HttpStatus.BAD_REQUEST);
            errorResponse.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

//    @PostMapping("/login")
//    public void memberLogin(@RequestBody MemberRequestDto memberRequestDto){
//
//    }
    
}
