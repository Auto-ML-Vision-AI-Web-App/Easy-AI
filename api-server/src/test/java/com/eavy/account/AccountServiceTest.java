package com.eavy.account;

import com.eavy.token.TokenManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void loadByUsername_success() {
        String username = "USERID";
        String password = "PASSWORD";
        Account account = new Account(username, password);
        accountService.signUp(account);

        UserDetailsService userDetailsService = this.accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test
    void loadByUsername_fail() {
        UserDetailsService userDetailsService = this.accountService;
        assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = userDetailsService.loadUserByUsername("USERID");
        });
    }

    @Test
    void logout() {
        String username = "test-user";
        String refreshToken = "test-refresh-token";
        TokenManager.save(username, refreshToken);

        accountService.logout(username);

        assertThat(TokenManager.contains(refreshToken)).isFalse();
    }

}