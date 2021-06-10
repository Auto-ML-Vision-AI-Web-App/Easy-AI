package com.eavy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DataControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceLoader resourceLoader;

    @DisplayName("Get data by projectId")
    @Test
    void getDataByProjectId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/data/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Image file upload - success")
    @Test
    void fileUploadSuccess() throws Exception {
        // TODO png에 대해 테스트 되지 않음
        String originalFilename = "test_file.jpg";
        MockMultipartFile mockFile = new MockMultipartFile("files", originalFilename, "image/jpeg", getClass().getResourceAsStream("/images/wakeupcat.jpg"));

        mockMvc.perform(multipart("/upload").file(mockFile))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("1")); // projectId
    }

    @DisplayName("Image file upload - fail(no image file)")
    @Test
    void fileUploadFail() throws Exception {
        // TODO txt 이외의 다른 형식에 대해 테스트 되지 않음
        String originalFilename = "test_file.txt";
        MockMultipartFile mockFile = new MockMultipartFile("file", originalFilename, "text/plain", getClass().getResourceAsStream("/images/test_file.txt"));

        mockMvc.perform(multipart("/data").file(mockFile))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().string(HttpStatus.BAD_REQUEST.getReasonPhrase()));
    }

}