package com.eavy.data;

import com.eavy.account.Account;
import com.eavy.common.ControllerTest;
import com.eavy.token.TokenManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DataControllerTest extends ControllerTest {

    @DisplayName("데이터(이미지) 업로드")
    @Test
    void fileUpload() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());

        String filename1 = "test_file.jpg";
        MockMultipartFile mockFile1 = new MockMultipartFile("files", filename1, "image/jpeg", getClass().getResourceAsStream("/images/test-image.jpg"));
        String filename2 = "test_file.png";
        MockMultipartFile mockFile2 = new MockMultipartFile("files", filename2, "image/jpeg", getClass().getResourceAsStream("/images/test-image.png"));

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile1)
                        .file(mockFile2)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("projectName", "test"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 실패 - 이미지가 아닌 파일")
    @Test
    void fileUploadFail_NotImage() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());

        String filename = "test-text.txt";
        MockMultipartFile mockFile = new MockMultipartFile("files", filename, "text/plain", "hello world".getBytes());

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 실패 - 업로드 할 데이터가 없는 경우")
    @Test
    void fileUploadFail_NoFiles() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());

        mockMvc.perform(multipart("/data/upload")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("데이터(이미지) 업로드 실패 - 유효하지 않은 토큰")
    @Test
    void fileUploadFail_NotValidToken() throws Exception {
        Account account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        String accessToken = TokenManager.generateAccessToken(account.getUserId());

        String filename = "test-text.txt";
        MockMultipartFile mockFile = new MockMultipartFile("files", filename, "text/plain", "hello world".getBytes());

        mockMvc.perform(multipart("/data/upload")
                        .file(mockFile)
                        .header("Authorization", "Bearer " + accessToken.substring(1)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}