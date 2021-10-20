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

    @DisplayName("경로 생성")
    @Test
    void generatePath() {
        String userId = "user1";
        String projectName = "prj1";
        String category = "train";
        String className = "class1";

        String path1 = dataService.generatePath(userId);
        String path2 = dataService.generatePath(userId, projectName);
        String path3 = dataService.generatePath(userId, projectName, category);
        String path4 = dataService.generatePath(userId, projectName, category, className);
        assertThat(path1).isEqualTo("user1/");
        assertThat(path2).isEqualTo("user1/prj1/");
        assertThat(path3).isEqualTo("user1/prj1/train/");
        assertThat(path4).isEqualTo("user1/prj1/train/class1/");
    }

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