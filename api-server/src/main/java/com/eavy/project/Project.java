package com.eavy.project;

import com.eavy.account.Account;
import com.eavy.tag.Tag;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue
    Integer id;
    String projectName;
    LocalDate created = LocalDate.now();
    LocalDate lastModified = LocalDate.now();
    @ManyToOne
    Account account;
    @ManyToMany
    List<Tag> tags = new ArrayList<>();
    @ElementCollection
    List<String> classes = new ArrayList<>();
    double accuracy;
    double loss;

    public Project() {
    }

    public void addTag(Tag tag) {
        this.getTags().add(tag);
        tag.getProjects().add(this);
    }

    public Project(String name) {
        this.projectName = name;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String name) {
        this.projectName = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
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
}
