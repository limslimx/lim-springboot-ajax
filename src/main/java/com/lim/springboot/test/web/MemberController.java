package com.lim.springboot.test.web;

import com.lim.springboot.test.domain.Member;
import com.lim.springboot.test.service.MemberRepository;
import com.lim.springboot.test.service.MemberService;
import com.lim.springboot.test.web.dto.MemberDto;
import com.lim.springboot.test.web.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    //회원가입 페이지
    @GetMapping("/user/signup")
    public String signUpForm() {
        log.info(this.getClass().getName()+".dispSignup start!");
        return "/signup";
    }

    //회원가입 처리
    @PostMapping("/user/signup")
    public String signUp(MemberDto memberDto) {
        log.info(this.getClass().getName() + ".signUp start!");
        log.info(this.getClass().getName() + ".uId: " + memberDto.getUId());
        log.info(this.getClass().getName() + ".email: " + memberDto.getEmail());
        log.info(this.getClass().getName() + ".password: " + memberDto.getPassword());

        Member member = memberService.joinUser(memberDto);
        log.info(this.getClass().getName() + ".role: " + memberDto.getRole());
        log.info(this.getClass().getName() + ".signUp end!");

        return "redirect:/user/login";
    }

    //로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "/login";
    }

    //로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "/loginSuccess";
    }

    //로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/logout";
    }

    //접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "/denied";
    }

    //내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo(Principal principal, Model model) {
        String name = principal.getName();
        Optional<Member> byuId = memberRepository.findByuId(name);
        log.info("byuId = " + byuId);
        model.addAttribute("uId", byuId.get().getUId());
        model.addAttribute("email", byuId.get().getEmail());
        model.addAttribute("role", byuId.get().getRole());
        return "/myinfo";
    }

    //어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }

}
