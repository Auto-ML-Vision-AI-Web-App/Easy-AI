package com.eavy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public String index() {
        return "eavy";
    }

    @GetMapping("/model")
    public AI getModel() {
        return new AI("MLPClassifier", "logistic", "adaptive",
                50, 0.1, "sgd");
    }

}
