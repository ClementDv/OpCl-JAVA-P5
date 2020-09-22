package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.DataFileAccess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/*@RunWith(SpringJUnit4ClassRunner.class)
public class GetTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataFileAccess dataFileAccess;

    @Before
    public void setup(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void simpleTest() throws Exception {
        mockMvc.perform(get("/data")).andExpect(status.isok)
    }


}*/
