package com.eavy;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppRunner implements ApplicationRunner {

    public static void main(String[] args) throws IOException {
//        Page<Blob> list = storage.list("breath-of-ai");
//        list.iterateAll().forEach(b -> {
//            URL url = b.signUrl(15l, TimeUnit.MINUTES);
//            System.out.println(url);
//        });
    }

    private final ResourceLoader resourceLoader;

    public AppRunner(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
