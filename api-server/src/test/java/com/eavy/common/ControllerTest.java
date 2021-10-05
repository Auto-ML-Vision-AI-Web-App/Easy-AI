package com.eavy.common;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected ResourceLoader resourceLoader;

    protected final String TEST_ID = "TESTID";
    protected final String TEST_PASSWORD = "TESTPASSWORD";

    protected Account testUser;
    protected String accessToken;

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
