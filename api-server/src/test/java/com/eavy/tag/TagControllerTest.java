package com.eavy.tag;

import com.eavy.common.ControllerTest;
import com.eavy.project.Project;
import com.eavy.project.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
class TagControllerTest extends ControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Test
    @DisplayName("모든 태그 조회")
    void getTags() throws Exception {
        Tag tag1 = new Tag("dog");
        Tag tag2 = new Tag("cat");
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
        Project project1 = new Project("prj1");
        Project project2 = new Project("prj2");
        Tag tag = new Tag("dog");
        project1.getTags().add(tag);
        project2.getTags().add(tag);
        tag.getProjects().add(project1);
        tag.getProjects().add(project2);
        tagRepository.save(tag);
        projectRepository.save(project1);
        projectRepository.save(project2);

        ObjectMapper objectMapper = new ObjectMapper();
        String project1Json = objectMapper.writeValueAsString(project1);
        String project2Json = objectMapper.writeValueAsString(project2);

        mockMvc.perform(get("/tags")
                        .param("tagName", "dog"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(project1Json)))
                .andExpect(content().string(containsString(project2Json)))
                .andDo(print());
    }

}