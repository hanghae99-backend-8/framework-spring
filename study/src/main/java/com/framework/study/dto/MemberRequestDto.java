package com.framework.study.dto;

import com.framework.study.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String username;
    private String password;

    @Builder
    public MemberRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Member toEntity(){
        return  new Member(username, password);
    }

}
