package com.eavy.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public Storage storage() throws IOException {
        Storage storage = StorageOptions.newBuilder().setCredentials(ServiceAccountCredentials.fromStream(resourceLoader.getResource("classpath:breath-of-ai-282d25539b0d.json").getInputStream())).build().getService();
        return storage;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
