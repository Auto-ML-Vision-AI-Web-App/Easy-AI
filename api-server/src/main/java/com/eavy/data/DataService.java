package com.eavy.data;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class DataService {

    private final Storage storage;

    @Value("${BUCKET_NAME}")
    private String bucketName;

    public DataService(Storage storage) {
        this.storage = storage;
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

    public String generatePath(String userId, String projectName) {
        return generatePath(userId, projectName, null);
    }
}
