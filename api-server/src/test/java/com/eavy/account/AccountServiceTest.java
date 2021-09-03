package com.eavy.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void loadByUsername_success() {
        String userId = "USERID";
        String password = "PASSWORD";
        Account account = new Account(userId, password);
        account.setRoles(Set.of(AccountRole.ADMIN, AccountRole.USER));
        accountRepository.save(account);

        UserDetailsService userDetailsService = this.accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        assertThat(userDetails.getPassword()).isEqualTo(password);
    }

    @Test
    void loadByUsername_fail() {
        UserDetailsService userDetailsService = this.accountService;
        assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = userDetailsService.loadUserByUsername("USERID");
        });
    }

}