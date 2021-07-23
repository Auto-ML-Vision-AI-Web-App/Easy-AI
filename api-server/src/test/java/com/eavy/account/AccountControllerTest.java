package com.eavy.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("[로그인 성공] DB에 저장된 정보와 일치하는 경우")
    @Test
    void signInSuccess() throws Exception {
        Account account = new Account("donghun", "1031");
        accountRepository.save(account);

        mockMvc.perform(post("/signin")
            .param("username", account.getUsername())
            .param("password", account.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[로그인 실패] DB에 저장된 정보와 일치하지 않는 경우")
    @Test
    void signInFailure() throws Exception {
        mockMvc.perform(post("/signin")
                .param("username", "SUPERAWESOMEID")
                .param("password", "SUPERSECUREPASSWORD"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}