package com.safetynet.alerts.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.TestConfig;
import com.safetynet.alerts.repository.DataFileAccess;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class FirestationServiceImplIntegrationTest {

    @Autowired
    private DataFileAccess dataFileAccess;

    @MockBean
    private ObjectMapper objectMapper;


}
