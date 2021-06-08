package com.eavy;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppRunner implements ApplicationRunner {

    public static void main(String[] args) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "hello");
        System.out.println(jsonObject.toString());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "world");
        System.out.println(jsonObject2.toString());
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject2);
        System.out.println(jsonArray.toString());
    }

    private final ResourceLoader resourceLoader;

    public AppRunner(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
