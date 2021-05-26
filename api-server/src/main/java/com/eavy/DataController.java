package com.eavy;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DataController {

    private final ResourceLoader resourceLoader;

    public DataController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/data")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
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
