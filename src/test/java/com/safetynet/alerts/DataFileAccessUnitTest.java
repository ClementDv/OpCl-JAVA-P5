package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DataFileAccessUnitTest {

    @Autowired
    private DataFileAccess dataFileAccess;

    @MockBean
    private ObjectMapper objectMapper;

    @Before
    public void setup() throws IOException {
        Mockito.when(objectMapper.readValue(Mockito.any(File.class), Mockito.eq(DataFile.class))).thenReturn(new DataFile(List.of(), List.of(new Firestations("3", 3)), List.of()));
    }

    @Test
    public void getNbStationByAddressFromPersonTest() {
        int toCheck = dataFileAccess.getNbStationByAddressFromPerson(new Person("Brian",
                "Stelzer",
                "3",
                "Culver",
                "97451",
                "841-874-7784",
                "bstel@email.com"));
        Assertions.assertThat(toCheck).isEqualTo(3);
    }
}
