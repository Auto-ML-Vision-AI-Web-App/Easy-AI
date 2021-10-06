package com.eavy.account;

import com.eavy.common.ControllerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class AccountControllerTest extends ControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    void setUp() throws Exception {
        String userId = "qwer";
        String password = "1234";
        testUser = accountService.signUp(new Account(userId, password, null));
        accessToken = generateToken(userId, password);
    }

    @DisplayName("로그인")
    @Test
    void signIn() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD, Set.of(AccountRole.ADMIN, AccountRole.USER));
        accountService.signUp(account);

        mockMvc.perform(post("/signin")
            .param("userId", TEST_ID)
            .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access-token").exists())
                .andExpect(jsonPath("refresh-token").exists());
    }

    @DisplayName("로그인 실패")
    @Test
    void signInFailure_() throws Exception {
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

    @DisplayName("회원가입 실패 - 비어 있는 값")
    @Test
    void signUpFailure_emptyValue() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD, Set.of(AccountRole.ADMIN, AccountRole.USER));
        accountService.signUp(account);

        mockMvc.perform(post("/signup")
                        .param("userId", "")
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/signup")
                        .param("userId", TEST_ID)
                        .param("password", ""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원가입 실패 - 중복된 아이디")
    @Test
    void signUpFailure_usernameAlreadyExists() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD, Set.of(AccountRole.ADMIN, AccountRole.USER));
        accountService.signUp(account);

        mockMvc.perform(post("/signup")
                        .param("userId", TEST_ID)
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 조회")
    @Test
    void getUser() throws Exception {
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(testUser.getUserId()))
                .andExpect(jsonPath("$.numOfProjects").exists());
    }

    @DisplayName("회원 조회 실패 - 유효하지 않은 토큰")
    @Test
    void getUserFailure_notValidToken() throws Exception {
        mockMvc.perform(get("/users")
                        .param("userId", testUser.getUserId())
                        .header("Authorization", "Bearer " + "FAKE-ACCESS-TOKEN"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @DisplayName("토큰 검사")
    @Test
    void checkToken() throws Exception {
        mockMvc.perform(get("/token/check")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/token/check")
                        .header("Authorization", "Bearer " + "FAKE-ACCESS-TOKEN"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}