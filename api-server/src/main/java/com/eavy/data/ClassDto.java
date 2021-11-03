package com.eavy.data;

import java.util.HashMap;

public class ClassDto {

    int all;
    private HashMap<String, Integer> classNameToSize = new HashMap<>();

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public HashMap<String, Integer> getClassNameToSize() {
        return classNameToSize;
    }

    public void setClassNameToSize(HashMap<String, Integer> classNameToSize) {
        this.classNameToSize = classNameToSize;
    }

}
