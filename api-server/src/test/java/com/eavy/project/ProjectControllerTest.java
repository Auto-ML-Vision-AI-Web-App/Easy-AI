package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.common.ControllerTest;
import com.eavy.token.TokenManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest extends ControllerTest {

    @Autowired
    private ProjectRepository projectRepository;

    @DisplayName("프로젝트 업데이트")
    @Test
    void updateProject() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        Project project = new Project("prj1");
        account.addProject(project);
        Project savedProject = projectRepository.save(project);
        String accessToken = TokenManager.generateAccessToken(TEST_ID);

        double accuracy = 0.82;
        double loss = 0.26;
        List<String> classes = List.of("c1, c2");

        mockMvc.perform(patch("/projects")
                        .param("projectName", project.getName())
                        .param("accuracy", "0.82")
                        .param("classes", classes.get(0), classes.get(1))
                        .param("loss", "0.26")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(project.getName()));

        assertThat(savedProject.getAccuracy()).isEqualTo(accuracy);
        assertThat(savedProject.getLoss()).isEqualTo(loss);
        assertThat(savedProject.getClasses()).isEqualTo(classes);
    }

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