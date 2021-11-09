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
                                                  Path path){
        path.setUserId(principal.getName());
        Map<String, String> filenameToUrl = dataService.getFilenameAndUrl(path.toString());
        if(filenameToUrl.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filenameToUrl);
    }

    @ResponseBody
    @GetMapping(produces = "application/zip")
    public void downloadDataAsZip(Principal principal,
                                  Path path,
                                  HttpServletResponse response) throws IOException {
        path.setUserId(principal.getName());

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        dataService.zipFilesInPathAndWriteTo(path.toString(), response.getOutputStream());
    }

    @PostMapping("/upload")
    public ResponseEntity uploadImageFiles(Principal principal,
                                           Path path,
                                           @NotEmpty @RequestParam MultipartFile[] files) throws IOException {

        path.setUserId(principal.getName());

        // TODO projectName, className, category 모두 클래스로 분리하고 이에 대한 validation
        if(path.getProjectName().isEmpty()) {
            return ResponseEntity.badRequest().body("Project name should not be empty");
        }
        if(path.getClassName() != null && path.getClassName().isEmpty()) {
            return ResponseEntity.badRequest().body("Class name should not be empty");
        }
        if(path.getCategory().isEmpty()) {
            return ResponseEntity.badRequest().body("Category should not be empty");
        }

        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            if (dataService.isImageFile(file)) {
                dataService.uploadFileToStorage(path.toString(), file);
                successList.add(filename);
            } else {
                failList.add(filename);
            }
        }

        UploadResultDto result = new UploadResultDto(path.getClassName(), files.length,
                successList.size(), successList, failList.size(), failList);

        return ResponseEntity.ok().body(result);
    }

}