package com.eavy.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AccountControllerTest {

    private final MockMvc mockMvc;
    private final AccountRepository accountRepository;

    AccountControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("[login 성공] DB에 저장된 정보와 일치하는 경우")
    @Test
    void loginSuccess() throws Exception {
        Account account = new Account("donghun", 1031);
        accountRepository.save(account);

        mockMvc.perform(post("/login")
            .param("username", account.getUsername())
            .param("password", account.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[login 실패] DB에 저장된 정보와 일치하지 않는 경우")
    @Test
    void loginFailure() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "SUPERAWESOMEID")
                .param("password", "SUPERSECUREPASSWORD"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}