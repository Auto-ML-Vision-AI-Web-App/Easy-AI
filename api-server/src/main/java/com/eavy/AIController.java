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
