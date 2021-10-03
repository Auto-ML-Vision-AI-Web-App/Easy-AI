package com.eavy.account;

import com.eavy.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class AccountControllerTest extends ControllerTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("로그인")
    @Test
    void signInSuccess() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD, Set.of(AccountRole.ADMIN, AccountRole.USER));
        accountService.signUp(account);

        // TODO save한 객체를 그대로 쓰는게 best prectice인걸로 알고 있는데, 그럼 encoded password 객체를 받아서 사용하는게 맞는지?
        mockMvc.perform(post("/signin")
            .param("userId", TEST_ID)
            .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access-token").exists())
                .andExpect(jsonPath("refresh-token").exists());
    }

    @DisplayName("로그인 실패 - 정보 불일치")
    @Test
    void signInFailure() throws Exception {
        mockMvc.perform(post("/signin")
                .param("userId", TEST_ID)
                .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("회원가입")
    @Test
    void signUpSuccess() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("userId", TEST_ID)
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입 실패 - 중복된 아이디")
    @Test
    void signUpFailure() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD, Set.of(AccountRole.ADMIN, AccountRole.USER));
        accountService.signUp(account);

        mockMvc.perform(post("/signup")
                        .param("userId", TEST_ID)
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}