package com.framework.study.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/member")
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @GetMapping("")
    public String getMembermain() {
        return "member";
    }
    
}
