package com.eavy.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDto {
    String userId;
    String projectName;
    LocalDate created;
    LocalDate lastModified;
    double accuracy;
    double loss;
    List<String> classes = new ArrayList<>();
    List<String> tags = new ArrayList<>();

    public ProjectDto() {
    }

    public ProjectDto(Project project) {
        this.userId = project.getAccount().getUserId();
        this.projectName = project.getProjectName();
        this.created = project.getCreated();
        this.lastModified = project.getLastModified();
        this.accuracy = project.getAccuracy();
        this.loss = project.getLoss();
        this.classes = project.getClasses();
        project.getTags().forEach(tag -> this.tags.add(tag.getName()));
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
