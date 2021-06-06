package com.eavy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class AIController {

    private final AIRepository repository;

    public AIController(AIRepository repository) {
        this.repository = repository;
    }

    // for demo
    @GetMapping("/model")
    public ResponseEntity<String> getModelForDemo(@RequestParam String modelName) {
        String url = null;
        if(modelName.equals("image-classification")) {
            url = "https://drive.google.com/file/d/1s4iHkIf7iyanAAqslq6rPxNFf5phUj0o/view?usp=sharing";
        }
        else if(modelName.equals("object-detection")) {
            url = "https://drive.google.com/file/d/19TEc7SYQ1lh-6ZqCt6cNJybaTIqzpfXK/view?usp=sharing";
        }
        else if(modelName.equals("colorization")) {
            url = "https://drive.google.com/file/d/11xD8Q3bh_nMsK1RoqMG9niiiU6xkMaeK/view?usp=sharing";
        }
        else if(modelName.equals("style-transfer")) {
            url = "https://drive.google.com/file/d/1PZK_4OmlxH5lFlCKeG5m4NZwFilj_Irm/view?usp=sharing";
        }
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @GetMapping("/model/{modelId}")
    public ResponseEntity<AI> getModel(@PathVariable Long modelId) {
        Optional<AI> byId = repository.findById(modelId);
        if(byId.isPresent())
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/make-model")
    public ResponseEntity makeModel(@RequestParam String name) {
        if(name == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
        AI ai = new AI();
        ai.setName(name);
        // TODO save to repository
        return new ResponseEntity(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
    }

}
