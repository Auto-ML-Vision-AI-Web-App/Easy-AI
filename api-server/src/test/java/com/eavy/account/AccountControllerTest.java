package com.eavy.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String TEST_ID = "TESTID";
    private final String TEST_PASSWORD = "TESTPASSWORD";

    @DisplayName("[로그인 성공]")
    @Test
    void signInSuccess() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD);
        accountService.signUp(account);

        // TODO save한 객체를 그대로 쓰는게 best prectice인걸로 알고 있는데, 그럼 encoded password 객체를 받아서 사용하는게 맞는지?
        mockMvc.perform(post("/signin")
            .param("userId", account.getUserId())
            .param("password", account.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[로그인 실패] 정보 불일치")
    @Test
    void signInFailure() throws Exception {
        mockMvc.perform(post("/signin")
                .param("userId", TEST_ID)
                .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("[회원가입 성공]")
    @Test
    void signUpSuccess() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("userId", TEST_ID)
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[회원가입 실패] 중복된 아이디")
    @Test
    void signUpFailure() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD);
        accountService.signUp(account);

        mockMvc.perform(post("/signup")
                        .param("userId", account.getUserId())
                        .param("password", account.getPassword()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}