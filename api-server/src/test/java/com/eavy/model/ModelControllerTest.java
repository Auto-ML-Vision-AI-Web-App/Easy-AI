package com.eavy.model;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import com.eavy.model.Model;
import com.eavy.model.ModelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ModelRepository repository;

    @Autowired
    AccountService accountService;

    Account account;
    String accessToken;

    @BeforeAll
    void setUp() throws Exception {
        account = accountService.signUp(new Account("qwer", "1234", null));
        accessToken = generateToken(account.getUserId(), "1234");
    }

    @DisplayName("모델 조회")
    @Test
    void getModel() throws Exception {
        Model model = new Model("MLPClassifier", "logistic", "adaptive", 50, 0.1, "sgd");
        repository.save(model);

        mockMvc.perform(get("/models")
                        .param("modelId", model.getId().toString())
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(model.getName())))
                .andExpect(jsonPath("$.activation", is(model.getActivation())))
                .andExpect(jsonPath("$.learningRate", is(model.getLearningRate())))
                .andExpect(jsonPath("$.hiddenLayerSizes", is(model.getHiddenLayerSizes())))
                .andExpect(jsonPath("$.momentum", is(model.getMomentum())))
                .andExpect(jsonPath("$.solver", is(model.getSolver())))
                .andDo(print());
    }

    @DisplayName("모델 조회 실패 - 유효하지 않은 토큰")
    @Test
    void getModelFail_notValidToken() throws Exception {
        Model model = new Model("MLPClassifier", "logistic", "adaptive", 50, 0.1, "sgd");
        repository.save(model);

        mockMvc.perform(get("/models")
                        .param("modelId", model.getId().toString())
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

    // TODO 모델 생성 테스트
    /*@DisplayName("모델 생성")
    @Test
    void makeModelSuccess() throws Exception {
        mockMvc.perform(post("/models")
                .param("name", "LinearRegression"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("모델 생성 실패 - 유효하지 않은 토큰")
    @Test
    void makeModelFail() throws Exception {
        mockMvc.perform(post("/make-model"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }*/

    public String generateToken(String userId, String password) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/signin")
                        .param("userId", userId)
                        .param("password", password))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(content).get("access-token").toString();
    }

}