package com.eavy.common;

import com.eavy.account.Account;
import com.eavy.account.AccountService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
@Transactional
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

}
