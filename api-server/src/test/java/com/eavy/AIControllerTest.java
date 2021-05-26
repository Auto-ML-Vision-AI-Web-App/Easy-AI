package com.eavy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
class AIControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceLoader resourceLoader;

    @DisplayName("Get model info")
    @Test
    void getModelInfo() throws Exception {
        mockMvc.perform(get("/model"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("MLPClassifier")))
                .andExpect(jsonPath("$.activation", is("logistic")))
                .andExpect(jsonPath("$.learningRate", is("adaptive")))
                .andExpect(jsonPath("$.hiddenLayerSizes", is(50)))
                .andExpect(jsonPath("$.momentum", is(0.1)))
                .andExpect(jsonPath("$.solver", is("sgd")))
                .andDo(print());
    }

}