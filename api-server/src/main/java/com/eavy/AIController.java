package com.eavy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

}
