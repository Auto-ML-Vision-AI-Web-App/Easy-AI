package com.eavy.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RequestMapping("/data")
@Controller
public class DataController {

    private final DataService dataService;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public DataController(ResourceLoader resourceLoader, DataService dataService, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.dataService = dataService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<DataDto>> getData(Principal principal,
                                                 @RequestParam String projectName){
        String path = dataService.generatePath(principal.getName(), projectName);
        List<DataDto> allData = dataService.getData(path);
        if(allData.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allData);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadImageFiles(Principal principal,
                                           @RequestParam @NotEmpty String projectName,
                                           @RequestParam(required = false) @NotEmpty String className,
                                           @RequestParam @NotEmpty String category,
                                           @RequestParam @NotEmpty MultipartFile[] files) throws IOException {
        for (MultipartFile multipartFile : files) {
            if (!dataService.isImageFile(multipartFile)) {
                return ResponseEntity.badRequest().build();
            }
        }

        String path = dataService.generatePath(principal.getName(), projectName, category, className);
        for(MultipartFile file : files) {
            dataService.uploadFileToStorage(path, file);
        }

        return ResponseEntity.ok().body(path);
    }

    @ResponseBody
    @GetMapping(produces = "application/zip")
    public void downloadDataAsZip(Principal principal,
                                  HttpServletResponse response) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        String path = dataService.generatePath(principal.getName());
        dataService.zipFilesInPathAndWriteTo(path, response.getOutputStream());
    }

}