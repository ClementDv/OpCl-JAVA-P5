package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repositoryTest.DataTestUtils;
import com.safetynet.alerts.service.MedicalRecordsService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MedicalRecordsServiceTest {

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    private DataFileAccess dataFileAccess;

    private static List<String> medicationsFromPersonList = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));

    private static List<String> allergiesFromPersonList = new ArrayList<>(List.of("nillacilan"));

    public static List<Person> personListTest = DataTestUtils.personListTest;

    public static List<Firestations> firestationsListTest = DataTestUtils.firestationsListTest;

    public static List<MedicalRecords> medicalRecordsListTest = DataTestUtils.medicalRecordsListTest;

    public static DataFile dataFileTest = new DataFile(personListTest, firestationsListTest, medicalRecordsListTest);

    @Before
    public void setup() throws IOException {
        Mockito.when(objectMapper.readValue(Mockito.any(File.class), Mockito.eq(DataFile.class))).thenReturn(dataFileTest);
    }

    @Test
    public void getMedicationFromPersonTest() {
        Assertions.assertThat(medicalRecordsService.getMedicationsFromPerson(personListTest.get(0))).isEqualTo(medicationsFromPersonList);
    }

    @Test
    public void getAllergiesFromPersonTest() {
        Assertions.assertThat(medicalRecordsService.getAllergiesFromPerson(personListTest.get(0))).isEqualTo(allergiesFromPersonList);
    }

    @Test
    public void saveMedicalRecordsTest() {
        List<MedicalRecords> medicalRecordsListSave = new ArrayList<>(dataFileAccess.loadDataFile().getMedicalrecords());
        Assertions.assertThat(medicalRecordsService.saveMedicalRecords(DataTestUtils.medicalRecordsToAddTest)).isEqualTo(DataTestUtils.medicalRecordsToAddTest);
        dataFileAccess.loadDataFile().setMedicalrecords(medicalRecordsListSave);
    }

    @Test
    public void updateMedicalRecordsTest() {
        List<MedicalRecords> medicalRecordsListSave = new ArrayList<>(dataFileAccess.loadDataFile().getMedicalrecords());
        Assertions.assertThat(medicalRecordsService.updateMedicalRecords(DataTestUtils.medicalRecordsToUpdateTest)).isEqualTo(DataTestUtils.medicalRecordsToUpdateTest);
        dataFileAccess.loadDataFile().setMedicalrecords(medicalRecordsListSave);
    }

    @Test
    public void deleteMedicalRecordsTest() {
        List<MedicalRecords> medicalRecordsListSave = new ArrayList<>(dataFileAccess.loadDataFile().getMedicalrecords());
        Assertions.assertThat(medicalRecordsService.deleteMedicalRecords(DataTestUtils.medicalRecordsToDeleteTest)).isEqualTo(true);
        dataFileAccess.loadDataFile().setMedicalrecords(medicalRecordsListSave);
    }
}
