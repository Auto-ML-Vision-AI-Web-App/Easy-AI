package com.eavy.data;

import com.eavy.account.Account;
import com.eavy.common.ControllerTest;
import com.eavy.token.TokenManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DataControllerTest extends ControllerTest {

    @SpyBean(name = "dataService")
    DataService dataService;

    @DisplayName("프로젝트의 모든 데이터 URL 조회")
    @Test
    void getDataUrl() throws Exception {
        //given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String projectName = "prj1";
        String category = "train";
        String path = account.getUserId() + "/" + projectName + "/" + category + "/";
        Map<String, String> filenameToUrl = new HashMap<>();
        filenameToUrl.put("filename1", "signUrl1");
        filenameToUrl.put("filename2", "signUrl2");
        given(dataService.getFilenameAndUrl(path)).willReturn(filenameToUrl);

        mockMvc.perform(get("/data/url")
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", projectName)
                        .param("category", category))
                .andExpect(content().string("{\"filename2\":\"signUrl2\",\"filename1\":\"signUrl1\"}"))
                .andDo(print());
    }

    @DisplayName("프로젝트의 모든 데이터 URL 조회 실패 - 해당 프로젝트가 없거나 파일이 없는 경우")
    @Test
    void getDataUrlFail_noContent() throws Exception {
        //given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String projectName = "prj1";
        String category = "train";
        String path = account.getUserId() + "/" + projectName + "/" + category + "/";
        given(dataService.getFilenameAndUrl(path)).willReturn(Map.of());

        mockMvc.perform(get("/data/url")
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", projectName)
                        .param("category", category))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @DisplayName("데이터 zip으로 다운로드")
    @Test
    void fileDownloadAsZip() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String projectName = "prj2";
        String category = "test";
        String path = account.getUserId() + "/" + projectName + "/" + category + "/";
        // method argument type 맞추지 않으면 UnfinishedStubbingException
        doNothing().when(dataService).zipFilesInPathAndWriteTo(anyString(), any(OutputStream.class));

        mockMvc.perform(get("/data")
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Accept", "application/zip")
                        .param("projectName", projectName)
                        .param("category", category))
                .andExpect(status().isOk());
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
        final int FILE_SIZE = 2;

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile1)
                        .file(mockFile2)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", projectName)
                        .param("category", category)
                        .param("className", className))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.className").value(className))
                .andExpect(jsonPath("$.all").value(FILE_SIZE))
                .andExpect(jsonPath("$.numOfSuccess").value(FILE_SIZE))
                .andExpect(jsonPath("$.successList").isNotEmpty())
                .andExpect(jsonPath("$.numOfFail").value(0))
                .andExpect(jsonPath("$.failList").isEmpty())
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
        int numOfFiles = 2;

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile1)
                        .file(mockFile2)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", projectName)
                        .param("category", category))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.all").value(numOfFiles))
                .andExpect(jsonPath("$.numOfSuccess").value(numOfFiles))
                .andExpect(jsonPath("$.successList").isNotEmpty())
                .andExpect(jsonPath("$.numOfFail").value(0))
                .andExpect(jsonPath("$.failList").exists())
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 - 이미지가 아닌 파일")
    @Test
    void fileUploadFail_NotImage() throws Exception {
        //given
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());
        String filename = "test-text.txt";
        MockMultipartFile mockFile = new MockMultipartFile("files", filename, "text/plain", "hello world".getBytes());
        String className = "dog";
        int numOfFiles = 1;

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", "test")
                        .param("category", "train")
                        .param("className", className))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.className").value(className))
                .andExpect(jsonPath("$.all").value(numOfFiles))
                .andExpect(jsonPath("$.numOfSuccess").value(0))
                .andExpect(jsonPath("$.successList").exists())
                .andExpect(jsonPath("$.numOfFail").value(numOfFiles))
                .andExpect(jsonPath("$.failList").isNotEmpty())
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