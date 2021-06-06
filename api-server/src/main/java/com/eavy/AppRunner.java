package com.eavy;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class AppRunner implements ApplicationRunner {

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println(AppRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    private final ResourceLoader resourceLoader;

    public AppRunner(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Path path = Paths.get("cl/images/");
        System.out.println(path.getFileName());
    }
}
