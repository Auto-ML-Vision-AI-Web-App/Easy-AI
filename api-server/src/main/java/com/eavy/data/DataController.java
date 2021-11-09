package com.eavy.data;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/data")
@Controller
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/url")
    public ResponseEntity getClassificationResult(Principal principal,
                                                  @RequestParam String projectName,
                                                  @RequestParam String category){
        String path = dataService.generatePath(principal.getName(), projectName, category);
        Map<String, String> filenameToUrl = dataService.getFilenameAndUrl(path);
        if(filenameToUrl.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filenameToUrl);
    }

    @ResponseBody
    @GetMapping(produces = "application/zip")
    public void downloadDataAsZip(Principal principal,
                             @RequestParam String projectName,
                             @RequestParam String category,
                             HttpServletResponse response) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        String path = dataService.generatePath(principal.getName(), projectName, category);
        dataService.zipFilesInPathAndWriteTo(path, response.getOutputStream());
    }

    @PostMapping("/upload")
    public ResponseEntity uploadImageFiles(Principal principal,
                                                            @RequestParam String projectName,
                                                            @RequestParam(required = false) String className,
                                                            @RequestParam String category,
                                                            @NotEmpty @RequestParam MultipartFile[] files) throws IOException {
        // TODO projectName, className, category 모두 클래스로 분리하고 이에 대한 validation
        if(projectName.isEmpty()) {
            return ResponseEntity.badRequest().body("Project name should not be empty");
        }
        if(className != null && className.isEmpty()) {
            return ResponseEntity.badRequest().body("Class name should not be empty");
        }
        if(category.isEmpty()) {
            return ResponseEntity.badRequest().body("Category should not be empty");
        }

        String path = dataService.generatePath(principal.getName(), projectName, category, className);

        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            if (dataService.isImageFile(file)) {
                dataService.uploadFileToStorage(path, file);
                successList.add(filename);
            } else {
                failList.add(filename);
            }
        }

        UploadResultDto result = new UploadResultDto(className, files.length,
                successList.size(), successList, failList.size(), failList);

        return ResponseEntity.ok().body(result);
    }

}