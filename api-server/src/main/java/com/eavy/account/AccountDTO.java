package com.eavy.account;

public class AccountDTO {

    private String username;
    private int numOfProjects;

    public AccountDTO(String username, int numOfProjects) {
        this.username = username;
        this.numOfProjects = numOfProjects;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumOfProjects() {
        return numOfProjects;
    }

    public void setNumOfProjects(int numOfProjects) {
        this.numOfProjects = numOfProjects;
    }
}
