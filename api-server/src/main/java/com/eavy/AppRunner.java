package com.eavy;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    private final ResourceLoader resourceLoader;
    private final AccountService accountService;

    public AppRunner(ResourceLoader resourceLoader, AccountService accountService) {
        this.resourceLoader = resourceLoader;
        this.accountService = accountService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account1 = new Account("h01010", "1234");
        Account account2 = new Account("db17", "asdf");
        Account account3 = new Account("kdh15", "qwer");
        accountService.signUp(account1);
        accountService.signUp(account2);
        accountService.signUp(account3);
    }
}
