package com.eavy.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @DisplayName("경로 생성")
    @Test
    void generatePath() {
        String userId = "user1";
        String projectName = "prj1";
        String category = "train";
        String className = "class1";

        Path path = new Path(userId);
        assertThat(path.toString()).isEqualTo(userId + "/");
        path.setProjectName(projectName);
        assertThat(path.toString()).isEqualTo(userId + "/" + projectName + "/");
        path.setCategory(category);
        assertThat(path.toString()).isEqualTo(userId + "/" + projectName + "/" + category + "/");
        path.setClassName(className);
        assertThat(path.toString()).isEqualTo(userId + "/" + projectName + "/" + category + "/" + className + "/");
    }

}
