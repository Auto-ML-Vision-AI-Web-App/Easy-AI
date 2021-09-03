package com.eavy.account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
            return null; // 이미 존재하는 아이디
        }
        Account AccountWithEncodedPassword = new Account(account.getUserId(), passwordEncoder.encode(account.password));
        return accountRepository.save(AccountWithEncodedPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + " not found in the database"));
        return new User(account.userId, account.getPassword(), authorities(account.getRoles()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }
}
