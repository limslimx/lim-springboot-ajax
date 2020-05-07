package com.lim.springboot.test.web.dto;

import com.lim.springboot.test.domain.Member;
import com.lim.springboot.test.domain.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
public class MemberDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String uId;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호 양식에 맞게 작성해 주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
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
