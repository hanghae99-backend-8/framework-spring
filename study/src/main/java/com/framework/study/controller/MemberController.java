package com.framework.study.controller;

import com.framework.study.domain.Member;
import com.framework.study.dto.MemberRequestDto;
import com.framework.study.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


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
    public String memberRegister(@RequestBody MemberRequestDto memberRequestDto){
        memberService.memberResister(memberRequestDto);
        return "ok";
    }
    
}
