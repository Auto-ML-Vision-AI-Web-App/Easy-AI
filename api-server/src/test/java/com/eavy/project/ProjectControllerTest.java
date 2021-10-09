package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.common.ControllerTest;
import com.eavy.token.TokenManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest extends ControllerTest {

    @Autowired
    private ProjectRepository projectRepository;

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

    @DisplayName("프로젝트 생성 실패 - 중복된 프로젝트 이름")
    @Test
    void createProjectFail_AlreadyExists() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        Project project = new Project("prj1");
        account.addProject(project);
        projectRepository.save(project);
        String accessToken = TokenManager.generateAccessToken(TEST_ID);

        mockMvc.perform(post("/projects")
                        .param("projectName", project.getName())
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}