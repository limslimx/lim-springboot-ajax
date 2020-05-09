package com.lim.springboot.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/member/index")
    public String memberIndex() {
        return "member/index";
    }

    @GetMapping("/test")
    public String test() {
        return "login2";
    }
}
