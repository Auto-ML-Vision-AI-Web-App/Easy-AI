package com.eavy.data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyFileList {

    List<MyFile> files = new ArrayList<>();
    int size;

    public List<MyFile> getFiles() {
        return files;
    }

    public void setFiles(List<MyFile> files) {
        this.files = files;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

class MyFile {
    String filename;
    URL url;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public MyFile(String filename, URL url) {
        this.filename = filename;
        this.url = url;
    }
}