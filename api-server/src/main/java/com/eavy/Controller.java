package com.eavy;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final ResourceLoader resourceLoader;

    // *******************************
    /* meaningless method */
    @GetMapping("/")
    public String index() {
        return "eavy";
    }

    @GetMapping("/model")
    public AI getModel() {
        return new AI("MLPClassifier", "logistic", "adaptive",
                50, 0.1, "sgd");
    }
    // *******************************

    @PostMapping("/file-upload")
    public String fileUpload(@RequestParam("file")MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        if(!extension.equals("jpg") && !extension.equals("png")) {
            return "fail";
        }

        byte[] bytes = file.getBytes();
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(
                new FileOutputStream("./src/main/resources/images/" + file.getOriginalFilename()));
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        return "success";
    }

}
