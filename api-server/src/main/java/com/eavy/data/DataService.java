package com.eavy.data;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class DataService {

    private final Storage storage;
    private final Tika tika;

    @Value("${BUCKET_NAME}")
    private String bucketName;

    public DataService(Storage storage) {
        this.storage = storage;
        this.tika = new Tika();
    }

    public ArrayList<BlobDto> getAllDataByPath(String path) {
        Page<Blob> list = storage.list(bucketName, Storage.BlobListOption.prefix(path));
        ArrayList<BlobDto> blobs = new ArrayList<>();
        list.iterateAll().forEach(b -> {
            if(b.getName().contains(".")) { // file
                BlobDto blobDto = new BlobDto();
                blobDto.setName(b.getName());
                blobDto.setSignUrl(b.signUrl(15L, TimeUnit.MINUTES));
                blobs.add(blobDto);
            }
        });
        return blobs;
    }

    public String generatePath(String userId, String projectName, String className) {
        String path = userId + "/" + projectName + "/";
        if(className != null && !className.isEmpty()) {
            path += className + "/";
        };
        return path;
    }

    public void uploadFileToStorage(String path, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, path + originalFilename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());
    }

    // TODO implement
/*    @DeleteMapping
    public ResponseEntity delete(Principal principal) {
        Bucket bucket = storage.get(bucketName);
        Page<Blob> list = bucket.list();
        list.iterateAll().forEach(b -> {
            if(b.getName().split("/")[0].equals(principal.getName()))
                b.delete();
        });
        return ResponseEntity.ok().build();
    }*/

    public String generatePath(String userId, String projectName) {
        return generatePath(userId, projectName, null);
    }

    public boolean isImageFile(MultipartFile file) {
        String mimeType = tika.detect(file.getOriginalFilename());
        return mimeType.startsWith("image");
    }
}