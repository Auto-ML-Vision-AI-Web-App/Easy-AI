package com.eavy;

import com.eavy.account.Account;
import com.eavy.account.AccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    private final ResourceLoader resourceLoader;
    private final AccountRepository accountRepository;

    public AppRunner(ResourceLoader resourceLoader, AccountRepository accountRepository) {
        this.resourceLoader = resourceLoader;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account1 = new Account("h01010", "1234");
        Account account2 = new Account("db17", "asdf");
        Account account3 = new Account("kdh15", "qwer");
        accountRepository.save(account1);
        accountRepository.save(account2);
        accountRepository.save(account3);
    }
}
