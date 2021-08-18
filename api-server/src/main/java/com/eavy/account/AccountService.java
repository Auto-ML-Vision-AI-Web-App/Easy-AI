package com.eavy.account;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO signIn(Account account) {
        Optional<Account> byUsername = accountRepository.findByUserId(account.getUserId());
        if(byUsername.isPresent()) {
            Account found = byUsername.get();
            return new AccountDTO(found.getUserId(), 1);
        }
        return null;
    }
}