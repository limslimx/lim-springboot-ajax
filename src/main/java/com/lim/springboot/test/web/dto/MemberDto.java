package com.lim.springboot.test.web.dto;

import com.lim.springboot.test.domain.Member;
import com.lim.springboot.test.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private String uId;
    private String email;
    private String password;
    private String passwordCheck;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Member toEntity() {
        return Member.builder()
                .uId(uId)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    @Builder
    public MemberDto(String uId, String email, String password, Role role) {
        this.uId = uId;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
