package com.eavy.data;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public Map<String, String> getFilenameAndUrl(String path) {
        File file = new File(path);
        HashMap<String, String> filenameToUrl = new HashMap<>();
        getFilenameAndUrlRecursively(path, filenameToUrl);
        return filenameToUrl;
    }

    private void getFilenameAndUrlRecursively(String path, Map<String, String> filenames) {
        Page<Blob> list = storage.list(bucketName, Storage.BlobListOption.prefix(path), Storage.BlobListOption.currentDirectory());
        list.iterateAll().forEach(b -> {
            filenames.put(b.getName(), b.signUrl(15L, TimeUnit.MINUTES).toString());
            if (b.isDirectory()) {
                getFilenameAndUrlRecursively(b.getName(), filenames);
            }
        });
    }

    public void zipFilesInPathAndWriteTo(String path, OutputStream destOutputStream) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(destOutputStream);
        zipFilesInPathAndWriteToRecursively(path, zipOutputStream);
        zipOutputStream.close();
    }

    private void zipFilesInPathAndWriteToRecursively(String path, ZipOutputStream zipOutputStream) throws IOException {
        Page<Blob> list = storage.list(bucketName, Storage.BlobListOption.prefix(path), Storage.BlobListOption.currentDirectory());
        for (Blob b : list.iterateAll()) {
            String name = b.getName();
            if(name.equals(path)) continue;
            String nameWithoutUserId = name.substring(name.indexOf('/') + 1);
            if (b.isDirectory()) {
                zipOutputStream.putNextEntry(new ZipEntry(nameWithoutUserId));
                zipOutputStream.closeEntry();
                zipFilesInPathAndWriteToRecursively(name, zipOutputStream);
            } else {
                ZipEntry zipEntry = new ZipEntry(nameWithoutUserId);
                zipOutputStream.putNextEntry(zipEntry);
                b.downloadTo(zipOutputStream);
            }
        }
    }

    public void uploadFileToStorage(String path, MultipartFile file) throws IOException {
        uploadFileToStorage(path, file, false);
    }

    public void uploadFileToStorage(String path, MultipartFile file, boolean isTestData) throws IOException {
        if (isTestData) {
            deleteByPath(path);
        }
        String originalFilename = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, path + originalFilename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());
    }

    public boolean isImageFile(MultipartFile file) {
        String mimeType = tika.detect(file.getOriginalFilename());
        return mimeType.startsWith("image");
    }

    private void deleteByPath(String path) {
        Bucket bucket = storage.get(bucketName);
        Page<Blob> list = bucket.list();
        list.iterateAll().forEach(b -> {
            if (b.getName().startsWith(path))
                b.delete();
        });
    }

}
