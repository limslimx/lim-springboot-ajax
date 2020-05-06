package com.lim.springboot.test.service;

import com.lim.springboot.test.domain.Member;
import com.lim.springboot.test.domain.Role;
import com.lim.springboot.test.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //회원가입 시에 사용하는 메서드로 회원가입 화면에서 비밀번호를 입력할 때, 단순히 문자 그대로를 저장하지 않고 암호화를 한 후에 저장한다. 이 과정이 없으면 나중에 로그인을 할 때 복호화 과정을 거치는데, 로그인을 할 수 없는 문제가 발생한다.
    @Transactional
    public Long joinUser(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (memberDto.getEmail().equals("admin@example.com")) {
            memberDto.setRole(Role.ADMIN);
        } else {
            memberDto.setRole(Role.MEMBER);
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(memberDto.toEntity()).getId();
    }

    //로그인을 하는데 있어 DB에 접근해서 사용자 정보를 가져오는 메서드이다.
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<Member> userEntityWrapper=memberRepository.findByEmail(userEmail);
        Member member = userEntityWrapper.get();

        List<GrantedAuthority> authorityList = new ArrayList<>();

        if (("admin@example.com").equals(userEmail)) {
            authorityList.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorityList.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(member.getEmail(), member.getPassword(), authorityList);
    }
}
