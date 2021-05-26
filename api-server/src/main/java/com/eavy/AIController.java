package com.eavy;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AIController {

    @GetMapping("/model")
    public AI getModel() {
        return new AI("MLPClassifier", "logistic", "adaptive",
                50, 0.1, "sgd");
    }

}
