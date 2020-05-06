package com.lim.springboot.test.web.dto;

import com.lim.springboot.test.domain.Member;
import com.lim.springboot.test.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    @Builder
    public MemberDto(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
