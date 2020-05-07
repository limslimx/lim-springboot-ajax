package com.lim.springboot.test.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uId;

    @Column(length = 125, nullable = false)
    private String email;

    @Column(length = 125, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String uId, String email, String password, Role role) {
        this.uId = uId;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
