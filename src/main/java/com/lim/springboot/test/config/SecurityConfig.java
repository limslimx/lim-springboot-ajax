package com.lim.springboot.test.config;

import com.lim.springboot.test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //Spring Security를 설정할 클래스라고 정의함 -> 일반적으로 WebSecurityConfigurerAdapter를 상속받아 사용함
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    //service에서 비밀번호를 암호화할 수 있도록 bean으로 등록함
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체임
    }

    //WebSecurity는 FilterChainProxy를 생성하는 필터임
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**"); //해당 경로의 파일들은 Spring Security가 무시할 수 있도록 설정함(파일 기준은 resources/static 디렉터리)
    }

    //HttpSecurity를 통해 Http 요청에 대한 웹 기반 보안을 구성함
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //HttpServletRequest에 따라 접근을 제한함
                .antMatchers("/admin/**").hasRole("ADMIN") //antMatchers() 메서드로 특정 경로를 지정하며 permitAll(), hasRole() 메서드로 역할에 따른 접근 설정을 잡아줌
                .antMatchers("/user/info").hasRole("MEMBER") //anyRequest().authenticated()는 모든 요청에 대해 인증된 사용자만 접근할 수 있도록 함
                .antMatchers("/**").permitAll()
            .and()
                .formLogin() //form기반으로 인증을 하도록 하고 로그인 정보는 기본적으로 HttpSession을 이용함. /login으로 접근하면 Spring Security에서 제공하는 로그인 form을 사용가능함
                .loginPage("/user/login") //기본 제공되는 form말고 커스텀 로그인 form을 사용하고 싶다면 loginPage() 메서드를 사용하면 됨. 단, 커스텀 로그인 form의 action경로와 loginPage()의 파라미터 경로가 일치해야 인증을 처리할 수 있음
                .defaultSuccessUrl("/user/login/result") //로그인 성공 시에 이동되는 페이지이며 controller에서 url매핑이 되어있어야 함
                .permitAll()
            .and()
                .logout() //로그아웃을 지원하는 메서드이며 기본적으로 /logout에 접근하면 Http 세션을 제거함
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) //로그아웃의 기본 url인 /logout이 아닌 다른 url로 재정의함
                .logoutSuccessUrl("/user/login") //로그아웃 성공시에 이동되는 페이지이며 controller에서 url매핑이 되어있어야 함
                .invalidateHttpSession(true) //Http세션을 초기화하는 작업임
            .and()
                .exceptionHandling().accessDeniedPage("/user/denied"); //예외가 발생했을 때 exceptionHandling() 메서드로 핸들링할 수 있음. 여기서는 접근권한이 없을 때, 로그인 페이지로 이동하도록 함
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
