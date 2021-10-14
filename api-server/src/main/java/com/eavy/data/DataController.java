package com.eavy.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/data")
@Controller
public class DataController {

    private final DataService dataService;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private final Storage storage;

    @Value("${BUCKET_NAME}")
    private String bucketName;

    public DataController(ResourceLoader resourceLoader, DataService dataService, ObjectMapper objectMapper, Storage storage) {
        this.resourceLoader = resourceLoader;
        this.dataService = dataService;
        this.objectMapper = objectMapper;
        this.storage = storage;
    }

    @DeleteMapping
    public ResponseEntity delete(Principal principal) {
        Bucket bucket = storage.get(bucketName);
        Page<Blob> list = bucket.list();
        list.iterateAll().forEach(b -> {
            if(b.getName().split("/")[0].equals(principal.getName()))
                b.delete();
        });
        return ResponseEntity.ok().build();
    }

    // TODO test
    @GetMapping
    public ResponseEntity<List<BlobDto>> getData(Principal principal,
                                                 @RequestParam String projectName){
        String path = dataService.generatePath(principal.getName(), projectName);
        ArrayList<BlobDto> allData = dataService.getAllDataByPath(path);
        if(allData.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(allData);
    }

    @PostMapping("/upload")
    public ResponseEntity fileUpload(Principal principal,
                                     @RequestParam String projectName,
                                     @RequestParam(required = false) String className,
                                     @RequestParam MultipartFile[] files) throws IOException {
        if(files.length == 0)
            return ResponseEntity.badRequest().build();
        if(projectName.isEmpty())
            return ResponseEntity.badRequest().body("project name is empty");

        Tika tika = new Tika();
        for(MultipartFile file : files) {
            String mimeType = tika.detect(file.getOriginalFilename());
            if (!mimeType.startsWith("image"))
                return ResponseEntity.badRequest().build();
        }

        String path = dataService.generatePath(principal.getName(), projectName, className);
        for(MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String mimeType = tika.detect(originalFilename);
            BlobId blobId = BlobId.of(bucketName, path + originalFilename);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file.getBytes());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload-local")
    public ResponseEntity<String> fileUploadLocal(@RequestParam("files") MultipartFile[] files) throws IOException {
        Tika tika = new Tika();
        for(MultipartFile file : files) {
            String mimeType = tika.detect(file.getOriginalFilename());
            if(!mimeType.startsWith("image"))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);

            byte[] bytes = file.getBytes();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream("./images/" + file.getOriginalFilename()));

            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
        return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
    }

}