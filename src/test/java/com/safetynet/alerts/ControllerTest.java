package com.safetynet.alerts;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.DataController;
import com.safetynet.alerts.data.CommonTestData;
import com.safetynet.alerts.data.FirestationTestData;
import com.safetynet.alerts.data.MedicalRecordTestData;
import com.safetynet.alerts.data.PersonTestData;
import com.safetynet.alerts.exception.firestation.NoFirestationFoundException;
import com.safetynet.alerts.exception.person.*;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.*;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repository.impl.DataFileAccessImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.naming.event.ObjectChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebMvcTest(DataController.class)
public class ControllerTest {

    @Autowired
    private DataFileAccess dataFileAccess;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    public static List<Person> personList = CommonTestData.getPersonList();

    public static List<Firestations> firestationsList = CommonTestData.getFirestationsList();

    public static List<MedicalRecords> medicalRecordsList = CommonTestData.getMedicalRecordsList();

    public static DataFile dataFileTest = new DataFile(personList, firestationsList, medicalRecordsList);


    @Before
    public void setup() {
        dataFileTest = new DataFile(CommonTestData.getPersonList(), CommonTestData.getFirestationsList(), CommonTestData.getMedicalRecordsList());
        ((DataFileAccessImpl) dataFileAccess).setDataFile(dataFileTest);
    }


    @Test
    public void getFirestationByIdTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={stationNumber}", 3).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        FirestationsZone firestationsZoneResult = objectMapper.readValue(result.getResponse().getContentAsString(), FirestationsZone.class);
        Assertions.assertThat(firestationsZoneResult).isEqualTo(FirestationTestData.getFirestationsZoneControllerTest());
    }

    @Test
    public void getFirestationByIdNoFirestationFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={stationNumber}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(mvcResult -> Assert.assertTrue(mvcResult.getResolvedException() instanceof NoFirestationFoundException))
                .andExpect(mvcResult -> Assert.assertEquals("No Firestation(s) found for number : [1] !", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    public void getChildAlertTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/childAlert?address={address}", "1509 Culver St")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ChildAlert childAlertResult = objectMapper.readValue(result.getResponse().getContentAsString(), ChildAlert.class);
        Assertions.assertThat(childAlertResult).isEqualTo(PersonTestData.getChildAlertTest());
    }


    @Test
    public void getChildAlertNoFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/childAlert?address={address}", "22 Rue du flan")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(mvcResult -> Assert.assertTrue(mvcResult.getResolvedException() instanceof NoChildFoundFromAddressException))
                .andExpect(mvcResult -> Assert.assertEquals("No child(ren) found for address : 22 Rue du flan !", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    public void getPhoneAlertFromFirestationsTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={firestation_number}", 2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<String> phoneNumberListResult = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertThat(phoneNumberListResult).isEqualTo(FirestationTestData.getPhoneAlertStringListTest());
    }

    @Test
    public void getPhoneAlertFromFirestationsNoFirestationFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={firestation_number}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(mvcResult -> Assert.assertTrue(mvcResult.getResolvedException() instanceof NoFirestationFoundException))
                .andExpect(mvcResult -> Assert.assertEquals("No Firestation(s) found for number : [1] !", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    public void getPersonInfosByAddressIfFireTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "1509 Culver St")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        FireMedicalRecord fireMedicalRecordResult = objectMapper.readValue(result.getResponse().getContentAsString(), FireMedicalRecord.class);
        Assertions.assertThat(fireMedicalRecordResult).isEqualTo(FirestationTestData.getFireMedicalRecordTest());
    }

    @Test
    public void getPersonInfosByAddressIfFireNoPersonFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "22 Rue du flan")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(mvcResult -> Assert.assertTrue(mvcResult.getResolvedException() instanceof NoPersonFoundFromAddressException))
                .andExpect(mvcResult -> Assert.assertEquals("No person(s) found for address : 22 Rue du flan !", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    public void getPersonInfoByStationsListTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={list of station numbers}", new int[]{3, 2})
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getPersonInfoByStationsListNoFirestationsFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={list of station numbers}", new int[]{3, 2, 1})
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(mvcResult -> Assert.assertTrue(mvcResult.getResolvedException() instanceof NoFirestationFoundException))
                .andExpect(mvcResult -> Assert.assertEquals("No Firestation(s) found for number : [1] !", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    public void getFullInfoByNameTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={firstName}&lastName={LastName}", null, "Boyd")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<FullInfoPerson> infoPersonListResult = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), FullInfoPerson[].class));
        Assertions.assertThat(infoPersonListResult).isEqualTo(PersonTestData.getFullInfoPersonByNameList());
    }

    @Test
    public void getFullInfoByNameNoPersonFoundFromFirstNameAndNameTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={firstName}&lastName={LastName}", "Jean", "Boyd")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(mvcResult -> Assert.assertTrue(mvcResult.getResolvedException() instanceof NoPersonFoundFromFirstNameAndNameException))
                .andExpect(mvcResult -> Assert.assertEquals("No person(s) named : Jean Boyd found!", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    public void getFullInfoByNameNoPersonFoundFromNameTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={firstName}&lastName={LastName}", null, "Mich")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(mvcResult -> Assert.assertTrue(mvcResult.getResolvedException() instanceof NoPersonFoundFromNameException))
                .andExpect(mvcResult -> Assert.assertEquals("No person(s) named : Mich found!", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    public void getEmailsFromCityTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Culver")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<String> emailsResult = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        Assertions.assertThat(emailsResult).isEqualTo(PersonTestData.getEmailsFromCityList());
    }

    @Test
    public void getEmailsFromCityNoPersonFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Paris")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(mvcResult -> Assert.assertTrue(mvcResult.getResolvedException() instanceof NoPersonFoundException))
                .andExpect(mvcResult -> Assert.assertEquals("No person(s) found!", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    public void savePersonTest() throws Exception {
        String jsonPersonToAdd = objectMapper.writeValueAsString(CommonTestData.getPersonToAddTest());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/person")
                .content(jsonPersonToAdd)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Person personResult = objectMapper.readValue(result.getResponse().getContentAsString(), Person.class);
        Assertions.assertThat(personResult).isEqualTo(CommonTestData.getPersonToAddTest());
    }

    @Test
    public void updatePersonTest() throws Exception {
        String jsonPersonToUpdate = objectMapper.writeValueAsString(CommonTestData.getPersonToUpdateTest());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/person")
                .content(jsonPersonToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Person personResult = objectMapper.readValue(result.getResponse().getContentAsString(), Person.class);
        Assertions.assertThat(personResult).isEqualTo(CommonTestData.getPersonToUpdateTest());
    }

    @Test
    public void deletePersonTest() throws Exception {
        String jsonPersonToDelete = objectMapper.writeValueAsString(CommonTestData.getPersonToDeleteTest());

        mvc.perform(MockMvcRequestBuilders.delete("/person")
                .content(jsonPersonToDelete)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andReturn();
    }


    @Test
    public void saveMedicalRecordTest() throws Exception {
        String jsonMedicalRecordToAdd = objectMapper.writeValueAsString(CommonTestData.getMedicalRecordsToAddTest());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .content(jsonMedicalRecordToAdd)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MedicalRecords medicalRecordResult = objectMapper.readValue(result.getResponse().getContentAsString(), MedicalRecords.class);
        Assertions.assertThat(medicalRecordResult).isEqualTo(CommonTestData.getMedicalRecordsToAddTest());
    }

    @Test
    public void updateMedicalRecordTest() throws Exception {
        String jsonMedicalRecordToUpdate = objectMapper.writeValueAsString(CommonTestData.getMedicalRecordsToUpdateTest());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                .content(jsonMedicalRecordToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MedicalRecords medicalRecordResult = objectMapper.readValue(result.getResponse().getContentAsString(), MedicalRecords.class);
        Assertions.assertThat(medicalRecordResult).isEqualTo(CommonTestData.getMedicalRecordsToUpdateTest());
    }

    @Test
    public void deleteMedicalRecordTest() throws Exception {
        String jsonMedicalRecordToDelete = objectMapper.writeValueAsString(CommonTestData.getMedicalRecordsToDeleteTest());

        mvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                .content(jsonMedicalRecordToDelete)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andReturn();
    }

    @Test
    public void saveFirestationTest() throws Exception {
        String jsonFirestationToAdd = objectMapper.writeValueAsString(CommonTestData.getFirestationToAdd());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/firestation")
                .content(jsonFirestationToAdd)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Firestations firestationResult = objectMapper.readValue(result.getResponse().getContentAsString(), Firestations.class);
        Assertions.assertThat(firestationResult).isEqualTo(CommonTestData.getFirestationToAdd());
    }

    @Test
    public void deleteFirestationTest() throws Exception {
        String jsonFirestationToDelete = objectMapper.writeValueAsString(CommonTestData.getFirestationToDelete());

        mvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .content(jsonFirestationToDelete)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andReturn();
    }

}
