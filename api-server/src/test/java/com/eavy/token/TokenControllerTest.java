package com.eavy.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TokenControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenManager tokenManager;

    @DisplayName("토큰 검사")
    @Test
    void checkToken() throws Exception {
        String accessToken = TokenManager.generateAccessToken(new User("qwer", "1234", List.of(new SimpleGrantedAuthority("TEST"))));

        mockMvc.perform(get("/token/check")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/token/check")
                        .header("Authorization", "Bearer " + "FAKE-ACCESS-TOKEN"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @DisplayName("토큰 재발급")
    @Test
    void refreshToken() throws Exception {
        String username = "test-user";
        String refreshToken = TokenManager.generateRefreshToken(new User(username, "1234", List.of(new SimpleGrantedAuthority("TEST"))));
        TokenManager.save(username, refreshToken);

        mockMvc.perform(get("/token/refresh")
                        .header("Authorization", "Bearer " + refreshToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access-token").exists())
                .andExpect(jsonPath("refresh-token").exists());

        TokenManager.remove(username);
    }

    @DisplayName("토큰 재발급 실패 - 로그아웃한 사용자의 토큰인 경우")
    @Test
    void refreshTokenFail_logoutUser() throws Exception {
        String refreshToken = TokenManager.generateRefreshToken(new User("qwer", "1234", List.of(new SimpleGrantedAuthority("TEST"))));

        mockMvc.perform(get("/token/refresh")
                        .header("Authorization", "Bearer " + refreshToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @DisplayName("토큰 재발급 실패 - 토큰값이 유효하지 않은 경우")
    @Test
    void refreshTokenFail_notValidToken() throws Exception {
        String username = "test-user";
        String notValidRefreshToken = "NOT-VALID-REFRESH-TOKEN";
        TokenManager.save(username, notValidRefreshToken);

        mockMvc.perform(get("/token/refresh")
                        .header("Authorization", "Bearer " + notValidRefreshToken))
                .andDo(print())
                .andExpect(status().isForbidden());

        TokenManager.remove(username);
    }

}