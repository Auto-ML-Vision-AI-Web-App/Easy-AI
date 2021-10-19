package com.eavy.data;

import java.net.URL;

public class DataDto {

    String name;
    URL signUrl;

    public DataDto() {
    }

    public DataDto(String name, URL signUrl) {
        this.name = name;
        this.signUrl = signUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(URL signUrl) {
        this.signUrl = signUrl;
    }
}