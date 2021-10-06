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

}