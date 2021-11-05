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

    // TODO 더 깔끔한 방법이 없을까
    public ClassDto getDataInfo(String path) {
        Page<Blob> list = storage.list(bucketName, Storage.BlobListOption.prefix(path), Storage.BlobListOption.currentDirectory());
        ClassDto classDto = new ClassDto();
        list.iterateAll().forEach(b -> {
            if(b.isDirectory()) { // class
                int count = 0;
                Page<Blob> classFiles = storage.list(bucketName, Storage.BlobListOption.prefix(b.getName()), Storage.BlobListOption.currentDirectory());
                for (Blob blob : classFiles.iterateAll()) {
                    count++;
                }
                String folderName = b.getName();
                int classNameIndex = folderName.contains("train") ? folderName.indexOf("train") + "train".length()+1 : folderName.indexOf("test") + "test".length()+1;
                classDto.getClassNameToSize().put(folderName.substring(classNameIndex, folderName.lastIndexOf('/')), count);
            }
        });
        return classDto;
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
            if(b.getName().equals(path)) continue;
            if (b.isDirectory()) {
                zipOutputStream.putNextEntry(new ZipEntry(b.getName()));
                zipOutputStream.closeEntry();
                zipFilesInPathAndWriteToRecursively(b.getName(), zipOutputStream);
            } else {
                ZipEntry zipEntry = new ZipEntry(b.getName());
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

    public void deleteUser(String userId) {
        deleteByPath(generatePath(userId));
    }

    public void deleteProject(String userId, String projectName) {
        deleteByPath(generatePath(userId, projectName));
    }

    public String generatePath(String userId, String projectName, String category, String className) {
        String path = "";
        if (userId != null && !userId.isEmpty()) {
            path += userId + "/";
            if (projectName != null && !projectName.isEmpty()) {
                path += projectName + "/";
                if (category != null && !category.isEmpty()) {
                    path += category + "/";
                    if (className != null && !className.isEmpty()) {
                        path += className + "/";
                    }
                }
            }
        }
        return path;
    }

    public String generatePath(String userId, String projectName, String category) {
        return generatePath(userId, projectName, category, null);
    }

    public String generatePath(String userId, String projectName) {
        return generatePath(userId, projectName, null, null);
    }

    public String generatePath(String userId) {
        return generatePath(userId, null, null, null);
    }

}
