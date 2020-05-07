package com.lim.springboot.test.web.dto;

import com.lim.springboot.test.domain.Member;
import com.lim.springboot.test.domain.Role;
import lombok.Getter;

import java.io.Serializable;
import java.util.Optional;

@Getter
public class SessionUser implements Serializable {

    private String uId;
    private String email;
    private Role role;

    public SessionUser(Member member) {
        this.uId = member.getUId();
        this.email = member.getEmail();
        this.role = member.getRole();
    }
}
