package com.eavy;

import com.eavy.model.Model;
import com.eavy.model.ModelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ModelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ModelRepository repository;

    // TODO 토큰과 함께 테스트
    /*@DisplayName("Get model info - success")
    @Test
    void getModelInfo() throws Exception {

        Model model = new Model("MLPClassifier", "logistic", "adaptive", 50, 0.1, "sgd");
        repository.save(model);

        mockMvc.perform(get("/model/" + model.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(model.getName())))
                .andExpect(jsonPath("$.activation", is(model.getActivation())))
                .andExpect(jsonPath("$.learningRate", is(model.getLearningRate())))
                .andExpect(jsonPath("$.hiddenLayerSizes", is(model.getHiddenLayerSizes())))
                .andExpect(jsonPath("$.momentum", is(model.getMomentum())))
                .andExpect(jsonPath("$.solver", is(model.getSolver())))
                .andDo(print());
    }

    @DisplayName("Get model info - fail")
    @Test
    void getModelInfoFail() throws Exception {

        mockMvc.perform(get("/model/9999"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("Make model - success")
    @Test
    void makeModelSuccess() throws Exception {
        mockMvc.perform(post("/make-model")
                .param("name", "LinearRegression"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Make model - fail")
    @Test
    void makeModelFail() throws Exception {
        mockMvc.perform(post("/make-model"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }*/

}