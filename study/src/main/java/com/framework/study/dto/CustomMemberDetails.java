package com.framework.study.dto;

import com.framework.study.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomMemberDetails implements UserDetails {

    private final Member member;

    public CustomMemberDetails(Member member){
        this.member  = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //role 값을 반환하는 영역 사용 안함

        return List.of();
    }

    @Override
    public String getPassword() {

        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }
}
