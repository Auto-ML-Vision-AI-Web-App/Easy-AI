package com.eavy.account;

public class AccountDTO {

    private String userId;
    private int numOfProjects;

    public AccountDTO(String userId, int numOfProjects) {
        this.userId = userId;
        this.numOfProjects = numOfProjects;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNumOfProjects() {
        return numOfProjects;
    }

    public void setNumOfProjects(int numOfProjects) {
        this.numOfProjects = numOfProjects;
    }
}
