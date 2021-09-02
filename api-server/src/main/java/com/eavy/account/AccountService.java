package com.eavy.account;

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
        Optional<Account> user = accountRepository.findByUserId(userId);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        if(user.isPresent()) {
            Account account = user.get();
            return new User(account.getUserId(), account.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User not found in the database");
    }
}
