package com.lim.springboot.test.web;

import com.lim.springboot.test.web.dto.MemberDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class MemberController {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));

    @PostMapping("/member/info")
    public List<MemberDto> memberInfo(@RequestBody MemberDto memberDto){
        log.info(this.getClass().getName() + ".memberInfo start!");

        List<MemberDto> memberDtoList = new ArrayList<>();
        memberDtoList.add(memberDto);

        log.info(this.getClass().getName() + ".memberInfo end!");
        return memberDtoList;
    }

    @PostMapping("/member/info2")
    public MemberDto memberInfo2(@RequestBody MemberDto memberDto) {
        log.info(this.getClass().getName() + ".memberInfo2 start!");

        if (memberDto.getAge().equals("20")) {
            memberDto.setTest("success");
        }
        return memberDto;
    }
}
