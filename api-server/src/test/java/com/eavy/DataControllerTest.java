package com.eavy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class DataControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceLoader resourceLoader;

    @DisplayName("Image file upload success")
    @Test
    void fileUploadSuccess() throws Exception {
        // TODO png에 대해 테스트 되지 않음
        String originalFilename = "test_file.jpg";
        MockMultipartFile mockFile = new MockMultipartFile("file", originalFilename, "image/jpeg", getClass().getResourceAsStream("/images/wakeupcat.jpg"));

        mockMvc.perform(multipart("/file-upload").file(mockFile))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("success"));
    }

    @DisplayName("Image file upload fail(no image file)")
    @Test
    void fileUploadFail() throws Exception {
        // TODO txt 이외의 다른 형식에 대해 테스트 되지 않음
        String originalFilename = "test_file.txt";
        MockMultipartFile mockFile = new MockMultipartFile("file", originalFilename, "text/plain", getClass().getResourceAsStream("/images/test_file.txt"));

        mockMvc.perform(multipart("/file-upload").file(mockFile))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("fail"));
    }

}