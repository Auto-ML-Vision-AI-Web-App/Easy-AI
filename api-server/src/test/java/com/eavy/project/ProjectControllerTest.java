package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.common.ControllerTest;
import com.eavy.token.TokenManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest extends ControllerTest {

    @DisplayName("프로젝트 생성")
    @Test
    void createProject() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(TEST_ID);
        String projectName = "prj1";
        mockMvc.perform(post("/projects")
                .param("projectName", projectName)
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(projectName));
    }

}