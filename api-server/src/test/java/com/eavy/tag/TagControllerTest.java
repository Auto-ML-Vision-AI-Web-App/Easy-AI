package com.eavy.tag;

import com.eavy.account.Account;
import com.eavy.account.AccountRepository;
import com.eavy.common.ControllerTest;
import com.eavy.project.Project;
import com.eavy.project.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
class TagControllerTest extends ControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("모든 태그 조회")
    void getTags() throws Exception {
        Tag tag1 = new Tag("t1");
        Tag tag2 = new Tag("t2");
        tagRepository.save(tag1);
        tagRepository.save(tag2);

        mockMvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(tag1.getName())))
                .andExpect(content().string(containsString(tag2.getName())))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 태그 조회")
    void getTag() throws Exception {
        Account account = new Account("u1", "cool-password");
        Project project1 = new Project("p1");
        Project project2 = new Project("p2");
        Tag tag = new Tag("t1");
        project1.getTags().add(tag);
        project2.getTags().add(tag);
        tag.getProjects().add(project1);
        tag.getProjects().add(project2);
        account.addProject(project1);
        account.addProject(project2);
        accountService.signUp(account);
        tagRepository.save(tag);
        projectRepository.save(project1);
        projectRepository.save(project2);

        mockMvc.perform(get("/tags/" + tag.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].projectName", hasItems(project1.getName(), project2.getName())))
                .andExpect(jsonPath("$[*].projectName", hasSize(tag.getProjects().size())))
                .andDo(print());
    }

    @Test
    @DisplayName("태그 생성")
    void createTag() throws Exception {
        Account account = accountService.signUp(new Account("user", "password"));
        Project project = new Project("p1");
        projectRepository.save(project);
        account.addProject(project);
        Tag tag = new Tag("t1");

        mockMvc.perform(post("/tags")
                        .param("projectName", project.getName())
                        .param("tagName", tag.getName()))
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(tagRepository.findByName(tag.getName())).isNotEmpty();
    }

    @Test
    @DisplayName("기존 태그에 프로젝트 추가")
    void addProjectToExistingTag() throws Exception {
        Tag tag = new Tag("t1");
        tagRepository.save(tag);

        Account account = accountService.signUp(new Account("user", "password"));

        Project project = new Project("p1");
        projectRepository.save(project);
        project.addTag(tag);
        Project newProject = new Project("p2");
        projectRepository.save(newProject);

        account.addProject(project);
        account.addProject(newProject);

        mockMvc.perform(post("/tags")
                        .param("projectName", newProject.getName())
                        .param("tagName", tag.getName()))
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(tagRepository.findByName(tag.getName())).isNotEmpty();
        assertThat(tag.getProjects().size()).isEqualTo(2);
    }

}