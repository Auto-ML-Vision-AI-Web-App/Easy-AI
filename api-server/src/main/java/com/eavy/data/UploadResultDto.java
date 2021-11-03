package com.eavy.data;

import java.util.ArrayList;
import java.util.List;

public class UploadResultDto {

    String className;
    int all;
    int numOfSuccess;
    List<String> successList;
    int numOfFail;
    List<String> failList;

    public UploadResultDto() {
        successList = new ArrayList<>();
        failList = new ArrayList<>();
    }

    public UploadResultDto(String className, int all, int numOfSuccess, List<String> successList, int numOfFail, List<String> failList) {
        this.className = className;
        this.all = all;
        this.numOfSuccess = numOfSuccess;
        this.successList = successList;
        this.numOfFail = numOfFail;
        this.failList = failList;
    }

    public List<String> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getNumOfSuccess() {
        return numOfSuccess;
    }

    public void setNumOfSuccess(int numOfSuccess) {
        this.numOfSuccess = numOfSuccess;
    }

    public int getNumOfFail() {
        return numOfFail;
    }

    public void setNumOfFail(int numOfFail) {
        this.numOfFail = numOfFail;
    }

    public List<String> getFailList() {
        return failList;
    }

    public void setFailList(List<String> failList) {
        this.failList = failList;
    }
}
