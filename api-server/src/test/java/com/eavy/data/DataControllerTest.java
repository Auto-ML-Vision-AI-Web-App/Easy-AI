package com.eavy.data;

import com.eavy.account.Account;
import com.eavy.common.ControllerTest;
import com.eavy.token.TokenManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URL;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DataControllerTest extends ControllerTest {

    @SpyBean(name = "dataService")
    DataService dataService;

    @DisplayName("데이터(이미지) 조회")
    @Test
    void getData() throws Exception {
        //given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String projectName = "prj1";
        String path = account.getUserId() + "/" + projectName + "/";
        List<DataDto> allData = List.of(new DataDto("dto", new URL("http://localhost:8080")));
        given(dataService.getData(path)).willReturn(allData);

        mockMvc.perform(get("/data")
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", projectName))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"dto\",\"signUrl\":\"http://localhost:8080\"}]"))
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드")
    @Test
    void fileUpload() throws Exception {
        // given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String projectName = "test";
        String category = "train";
        String className = "dog";
        String path = account.getUserId() + "/" + projectName + "/" + category + "/" + className + "/";
        String filename1 = "test_file.jpg";
        MockMultipartFile mockFile1 = new MockMultipartFile("files", filename1, "image/jpeg", getClass().getResourceAsStream("/images/test-image.jpg"));
        String filename2 = "test_file.png";
        MockMultipartFile mockFile2 = new MockMultipartFile("files", filename2, "image/jpeg", getClass().getResourceAsStream("/images/test-image.png"));

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile1)
                        .file(mockFile2)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", projectName)
                        .param("category", category)
                        .param("className", className))
                .andExpect(status().isOk())
                .andExpect(content().string(path))
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 - 클래스 이름이 없는 경우")
    @Test
    void fileUploadWithoutClassName() throws Exception {
        // given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String projectName = "hello";
        String category = "test";
        String path = account.getUserId() + "/" + projectName + "/" + category + "/";
        String filename1 = "test_file.jpg";
        MockMultipartFile mockFile1 = new MockMultipartFile("files", filename1, "image/jpeg", getClass().getResourceAsStream("/images/test-image.jpg"));
        String filename2 = "test_file.png";
        MockMultipartFile mockFile2 = new MockMultipartFile("files", filename2, "image/png", getClass().getResourceAsStream("/images/test-image.png"));

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile1)
                        .file(mockFile2)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", projectName)
                        .param("category", category))
                .andExpect(status().isOk())
                .andExpect(content().string(path))
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 실패 - 이미지가 아닌 파일")
    @Test
    void fileUploadFail_NotImage() throws Exception {
        //given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String filename = "test-text.txt";
        MockMultipartFile mockFile = new MockMultipartFile("files", filename, "text/plain", "hello world".getBytes());

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", "test")
                        .param("category", "train")
                        .param("className", "dog"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 실패 - 업로드 할 데이터가 없는 경우")
    @Test
    void fileUploadFail_noFiles() throws Exception {
        //given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());

        mockMvc.perform(multipart("/data/upload")
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", "test")
                        .param("category", "train")
                        .param("className", "dog"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 실패 - 프로젝트 이름이 비어있는 경우")
    @Test
    void fileUploadFail_emptyProjectName() throws Exception {
        //given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String filename = "test-text.txt";
        MockMultipartFile mockFile = new MockMultipartFile("files", filename, "text/plain", "hello world".getBytes());

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", "")
                        .param("category", "train")
                        .param("className", "dog"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 실패 - 유효하지 않은 토큰")
    @Test
    void fileUploadFail_NotValidToken() throws Exception {
        //given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String filename = "test-text.txt";
        MockMultipartFile mockFile = new MockMultipartFile("files", filename, "text/plain", "hello world".getBytes());

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile)
                        .header("Authorization", "Bearer " + accessToken.substring(1))
                        .param("projectName", "test")
                        .param("category", "train")
                        .param("className", "dog"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}