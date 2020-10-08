package com.safetynet.alerts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repository.impl.DataFileAccessImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataFileAccessUnitTest.Config.class)
public class DataFileAccessUnitTest {

    @ComponentScan(basePackageClasses = {DataFileAccess.class})
    static class Config {
        @Bean
        ObjectMapper objectMapper() { return Mockito.mock(ObjectMapper.class);}
    }

    @Autowired
    private DataFileAccess dataFileAccess;

    @Mock
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