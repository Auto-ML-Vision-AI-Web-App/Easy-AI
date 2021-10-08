package com.eavy.data;

import java.net.URL;

public class BlobDto {

    String name;
    URL signUrl;

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
