package com.eavy.data;

import javax.validation.constraints.NotBlank;

public class Path {
    String userId;
    @NotBlank
    String projectName;
    @NotBlank
    String category;
    String className;

    public Path() {
    }

    public Path(String userId, String projectName, String category, String className) {
        this.userId = userId;
        this.projectName = projectName;
        this.category = category;
        this.className = className;
    }

    public Path(String userId, String projectName, String category) {
        this(userId, projectName, category, null);
    }

    public Path(String userId, String projectName) {
        this(userId, projectName, null, null);
    }

    public Path(String userId) {
        this(userId, null, null, null);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String toString() {
        return generatePath(userId, projectName, category, className);
    }

    private String generatePath(String userId, String projectName, String category, String className) {
        String path = "";
        if (userId != null && !userId.isEmpty()) {
            path += userId + "/";
            if (projectName != null && !projectName.isEmpty()) {
                path += projectName + "/";
                if (category != null && !category.isEmpty()) {
                    path += category + "/";
                    if (className != null && !className.isEmpty()) {
                        path += className + "/";
                    }
                }
            }
        }
        return path;
    }

    private String generatePath(String userId, String projectName, String category) {
        return generatePath(userId, projectName, category, null);
    }

    private String generatePath(String userId, String projectName) {
        return generatePath(userId, projectName, null, null);
    }

    private String generatePath(String userId) {
        return generatePath(userId, null, null, null);
    }
}
