package com.lim.springboot.test.runner;

import com.lim.springboot.test.domain.Account;
import com.lim.springboot.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account lim = accountService.createAccount("lim", "1234");
        System.out.println("-----------------------------------------------");
        System.out.println(lim.getUsername()+" password: "+lim.getPassword());
        System.out.println("-----------------------------------------------");
    }
}
