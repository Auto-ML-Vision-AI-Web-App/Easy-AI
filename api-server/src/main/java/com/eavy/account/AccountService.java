package com.eavy.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AccountDTO signIn(Account account) {
        Optional<Account> byUsername = accountRepository.findByUserId(account.getUserId());
        if(byUsername.isPresent()) {
            Account found = byUsername.get();
            return new AccountDTO(found.getUserId(), 1);
        }
        return null;
    }

    public Account signUp(Account account) {
        Optional<Account> byUsername = accountRepository.findByUserId(account.getUserId());
        if(byUsername.isPresent()) {
            throw new RuntimeException("username '" + account.getUserId() + "' already exists");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + " not found in the database"));
        return new User(account.userId, account.getPassword(), Collections.<SimpleGrantedAuthority>emptyList());
    }

}
