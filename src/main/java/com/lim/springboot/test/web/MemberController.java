package com.lim.springboot.test.web;

import com.lim.springboot.test.domain.Member;
import com.lim.springboot.test.service.MemberRepository;
import com.lim.springboot.test.service.MemberService;
import com.lim.springboot.test.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
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
    public String signUpForm(MemberDto memberDto) {
        log.info(this.getClass().getName()+".signUpForm start!");
        return "/signup";
    }

    //회원가입 처리
    @PostMapping("/user/signup")
    public String signUp(@Valid MemberDto memberDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("memberDto", memberDto);
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                log.info(key);
                model.addAttribute(key, validatorResult.get(key));
            }

            return "/signup";
        }
        log.info(this.getClass().getName() + ".signUp start!");
        log.info(this.getClass().getName() + ".uId: " + memberDto.getUId());
        log.info(this.getClass().getName() + ".email: " + memberDto.getEmail());
        log.info(this.getClass().getName() + ".password: " + memberDto.getPassword());

        Member member = memberService.joinUser(memberDto);
        log.info(this.getClass().getName() + ".role: " + memberDto.getRole());
        log.info(this.getClass().getName() + ".signUp end!");

        return "redirect:/user/login";
    }

    //회원가입 유효성 검사 - 아이디
    @PostMapping("/user/signup/checkId")
    public @ResponseBody int checkId(@RequestBody String uId) {
        log.info(this.getClass().getName() + ".checkId start!");

        int count = memberRepository.countMemberByUId(uId);
        log.info(this.getClass().getName() + ".checkId end!");
        if (count == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //회원가입 유효성 검사 - 이메일
    @PostMapping("/user/signup/checkEmail")
    public @ResponseBody int checkEmail(@RequestBody String email) {
        log.info(this.getClass().getName() + ".checkEmail start!");

        int count = memberRepository.countMemberByEmail(email);
        log.info(this.getClass().getName() + ".checkEmail end!");
        if (count == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "login2";
    }

    //아이디 찾기 - form화면
    @GetMapping("/user/findId")
    public String findId() {
        return "findId";
    }

    //아이디 찾기 - 중복확인 로직
    @PostMapping("/user/findId")
    public @ResponseBody Optional<String> findId(@RequestBody String email) {
        Optional<String> id = memberService.findIdByEmail(email);
        log.info(String.valueOf(id));
        return id;
    }

    //로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult(Principal principal, Model model) {
        String name = principal.getName();
        model.addAttribute("name", name);
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
        model.addAttribute("member", byuId.get());
        return "/myinfo";
    }

    //어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }

}
