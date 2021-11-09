package com.eavy.data;

import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DataServiceTest {

    DataService dataService = new DataService(Mockito.mock(Storage.class));

    @DisplayName("이미지 파일 검사")
    @Test
    void isImageFile() throws IOException {
        String filename1 = "test_file.jpg";
        MockMultipartFile mockImageFile1 = new MockMultipartFile("files", filename1, "image/jpeg", getClass().getResourceAsStream("/images/test-image.jpg"));
        String filename2 = "test_file.png";
        MockMultipartFile mockImageFile2 = new MockMultipartFile("files", filename2, "image/png", getClass().getResourceAsStream("/images/test-image.png"));
        String filename3 = "test-text.txt";
        MockMultipartFile mockTextFile = new MockMultipartFile("files", filename3, "text/plain", "hello world".getBytes());

        assertThat(dataService.isImageFile(mockImageFile1)).isTrue();
        assertThat(dataService.isImageFile(mockImageFile2)).isTrue();
        assertThat(dataService.isImageFile(mockTextFile)).isFalse();
    }

}