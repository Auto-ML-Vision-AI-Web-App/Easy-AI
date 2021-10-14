package com.eavy.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tika.Tika;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final Tika tika;

    public DataController(ResourceLoader resourceLoader, DataService dataService, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.dataService = dataService;
        this.objectMapper = objectMapper;
        this.tika = new Tika();
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
    public ResponseEntity uploadFile(Principal principal,
                                     @RequestParam String projectName,
                                     @RequestParam(required = false) String className,
                                     @RequestParam MultipartFile[] files) throws IOException {
        if(files.length == 0)
            return ResponseEntity.badRequest().build();
        if(projectName.isEmpty())
            return ResponseEntity.badRequest().body("project name is empty");

        for(MultipartFile file : files) {
            String mimeType = tika.detect(file.getOriginalFilename());
            if (!mimeType.startsWith("image"))
                return ResponseEntity.badRequest().build();
        }

        String path = dataService.generatePath(principal.getName(), projectName, className);
        for(MultipartFile file : files) {
            dataService.uploadFileToStorage(path, file);
        }

        return ResponseEntity.ok().build();
    }

}