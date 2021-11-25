package com.eavy.account;

import com.eavy.common.ControllerTest;
import com.eavy.token.TokenManager;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends ControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("로그인")
    @Test
    void signIn() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD);
        accountService.signUp(account);

        MvcResult mvcResult = mockMvc.perform(post("/signin")
                        .param("username", TEST_ID)
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access-token").exists())
                .andExpect(jsonPath("refresh-token").exists())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(responseBody);
        String refreshToken = jsonObject.get("refresh-token").toString();

        assertThat(TokenManager.contains(refreshToken)).isTrue();
    }

    @DisplayName("로그인 실패")
    @Test
    void signInFailure() throws Exception {
        mockMvc.perform(post("/signin")
                .param("username", TEST_ID)
                .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("로그아웃")
    @Test
    @WithMockUser
    void logout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("회원가입")
    @Test
    void signUpSuccess() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", TEST_ID)
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입 실패 - 비어 있는 값")
    @Test
    void signUpFailure_emptyValue() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "")
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/signup")
                        .param("username", TEST_ID)
                        .param("password", ""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원가입 실패 - 중복된 아이디")
    @Test
    void signUpFailure_usernameAlreadyExists() throws Exception {
        Account account = new Account(TEST_ID, TEST_PASSWORD);
        accountService.signUp(account);

        mockMvc.perform(post("/signup")
                        .param("username", TEST_ID)
                        .param("password", TEST_PASSWORD))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 조회")
    @Test
    void getUser() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUsername());

        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(account.getUsername()))
                .andExpect(jsonPath("$.numOfProjects").exists());
    }

    @DisplayName("회원 조회 실패 - 유효하지 않은 토큰")
    @Test
    void getUserFailure_notValidToken() throws Exception {
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + "FAKE-ACCESS-TOKEN"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}