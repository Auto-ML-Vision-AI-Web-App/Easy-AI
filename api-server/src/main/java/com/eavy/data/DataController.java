package com.eavy.data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                                                  @Valid Path path,
                                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(e -> e.getCodes() != null ? e.getCodes()[0] : null).collect(Collectors.toCollection(ArrayList::new)));
        }

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
                                  @Valid Path path,
                                  BindingResult bindingResult,
                                  HttpServletResponse response) throws IOException {
        if(bindingResult.hasErrors()) {
            response.getOutputStream().println(bindingResult.getAllErrors().stream().map(e -> e.getCodes() != null ? e.getCodes()[0] : null).collect(Collectors.toCollection(ArrayList::new)).toString());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        path.setUserId(principal.getName());

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        dataService.zipFilesInPathAndWriteTo(path.toString(), response.getOutputStream());
    }

    @PostMapping("/upload")
    public ResponseEntity uploadImageFiles(Principal principal,
                                           @Valid Path path,
                                           BindingResult bindingResult,
                                           @NotEmpty @RequestParam MultipartFile[] files) throws IOException {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(e -> e.getCodes() != null ? e.getCodes()[0] : null).collect(Collectors.toCollection(ArrayList::new)));
        }

        path.setUserId(principal.getName());

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