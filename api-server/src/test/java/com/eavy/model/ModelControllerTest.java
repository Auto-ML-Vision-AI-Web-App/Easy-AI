package com.eavy.model;

import com.eavy.account.Account;
import com.eavy.common.ControllerTest;
import com.eavy.token.TokenManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ModelControllerTest extends ControllerTest {

    @Autowired
    ModelRepository modelRepository;

    private Account account;
    private String accessToken;
    private Model model;

    @BeforeAll
    void init() {
        account = accountService.signUp(new Account(TEST_ID, TEST_PASSWORD));
        accessToken = TokenManager.generateAccessToken(TEST_ID);
        model = new Model("MLPClassifier", "logistic", "adaptive", 50, 0.1, "sgd");
    }

    @DisplayName("모델 조회")
    @Test
    void getModel() throws Exception {
        Model savedModel = modelRepository.save(model);

        mockMvc.perform(get("/models")
                        .param("modelId", savedModel.getId().toString())
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(savedModel.getName())))
                .andExpect(jsonPath("$.activation", is(savedModel.getActivation())))
                .andExpect(jsonPath("$.learningRate", is(savedModel.getLearningRate())))
                .andExpect(jsonPath("$.hiddenLayerSizes", is(savedModel.getHiddenLayerSizes())))
                .andExpect(jsonPath("$.momentum", is(savedModel.getMomentum())))
                .andExpect(jsonPath("$.solver", is(savedModel.getSolver())))
                .andDo(print());
    }

    @DisplayName("모델 조회 실패 - 유효하지 않은 토큰")
    @Test
    void getModelFail_notValidToken() throws Exception {
        Model savedModel = modelRepository.save(model);

        mockMvc.perform(get("/models")
                        .param("modelId", savedModel.getId().toString())
                        .header("Authorization", "Bearer " + "FAKE-ACCESS-TOKEN"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("모델 조회 실패 - 존재하지 않는 모델")
    @Test
    void getModelFail_notFound() throws Exception {
        mockMvc.perform(get("/models")
                        .param("modelId", "987654321")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @DisplayName("모델 생성")
    @Test
    void createModel() throws Exception {
        mockMvc.perform(post("/models")
                        .param("name", model.getName())
                        .param("activation", model.getActivation())
                        .param("learningRate", model.getLearningRate())
                        .param("hiddenLayerSizes", String.valueOf(model.getHiddenLayerSizes()))
                        .param("momentum", String.valueOf(model.getMomentum()))
                        .param("solver", model.getSolver())
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(jsonPath("$.name", is(model.getName())))
                .andExpect(jsonPath("$.activation", is(model.getActivation())))
                .andExpect(jsonPath("$.learningRate", is(model.getLearningRate())))
                .andExpect(jsonPath("$.hiddenLayerSizes", is(model.getHiddenLayerSizes())))
                .andExpect(jsonPath("$.momentum", is(model.getMomentum())))
                .andExpect(jsonPath("$.solver", is(model.getSolver())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // TODO validation test or default value test
    /*@DisplayName("모델 생성 실패 - 모델 검증 실패")
    @Test
    void makeModelFail() throws Exception {
        mockMvc.perform(post("/make-model"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }*/

    @DisplayName("모델 생성 실패 - 유효하지 않은 토큰")
    @Test
    void createModelFail_notValidToken() throws Exception {
        mockMvc.perform(post("/make-model"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}