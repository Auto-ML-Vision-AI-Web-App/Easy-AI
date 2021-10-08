package com.eavy.account;

public class AccountDto {

    private String userId;
    private int numOfProjects;

    public AccountDto(String userId, int numOfProjects) {
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
