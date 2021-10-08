package com.eavy.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
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
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/data")
@Controller
public class DataController {

    private final ResourceLoader resourceLoader;
    private final Storage storage;
    private final ObjectMapper objectMapper;

    @Value("${BUCKET_NAME}")
    private String bucketName;

    public DataController(ResourceLoader resourceLoader, Storage storage, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.storage = storage;
        this.objectMapper = objectMapper;
    }

    // TODO test
    @GetMapping
    public ResponseEntity<List<BlobDto>> getData(Principal principal,
                                                 @RequestParam String projectName){
        String prefix = principal.getName() + "/" + projectName;
        Page<Blob> list = storage.list(bucketName, Storage.BlobListOption.prefix(prefix));
        ArrayList<BlobDto> blobs = new ArrayList<>();
        list.iterateAll().forEach(b -> {
            if(b.getName().contains(".")) { // file
                BlobDto blobDto = new BlobDto();
                blobDto.setName(b.getName());
                blobDto.setSignUrl(b.signUrl(15L, TimeUnit.MINUTES));
                blobs.add(blobDto);
            }
        });
        if(blobs.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(blobs);
    }

    @PostMapping("/upload")
    public ResponseEntity fileUpload(Principal principal,
                                     @RequestParam("files") MultipartFile[] files) throws IOException {
        if(files.length == 0)
            return ResponseEntity.badRequest().build();

        Tika tika = new Tika();
        for(MultipartFile file : files) {
            String mimeType = tika.detect(file.getOriginalFilename());
            if (!mimeType.startsWith("image"))
                return ResponseEntity.badRequest().build();
        }

        // TODO proejct id?
        // TODO naming 지금은 기존 데이터를 덮어쓸 수 있음
        int count = 1;
        String prefix = principal.getName() + "/";
        for(MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String mimeType = tika.detect(originalFilename);
            System.out.println(mimeType);
            BlobId blobId = BlobId.of(bucketName, prefix + count++ + originalFilename.substring(originalFilename.indexOf('.')));
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