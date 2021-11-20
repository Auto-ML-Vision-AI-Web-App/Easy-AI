package com.eavy.tag;

import com.eavy.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TagControllerTest extends ControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TagRepository tagRepository;

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
}