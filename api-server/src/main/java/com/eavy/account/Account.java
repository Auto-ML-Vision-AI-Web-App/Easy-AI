package com.eavy.account;

import com.eavy.project.Project;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue
    Integer id;
    @NotEmpty
    String userId;
    @NotEmpty
    String password;
    @OneToMany(mappedBy = "account")
    List<Project> projects;

    public Account(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String username) {
        this.userId = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}