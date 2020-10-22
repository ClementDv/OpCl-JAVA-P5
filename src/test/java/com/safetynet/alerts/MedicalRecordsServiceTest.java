package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.data.CommonTestData;
import com.safetynet.alerts.data.MedicalRecordTestData;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repository.impl.DataFileAccessImpl;
import com.safetynet.alerts.service.MedicalRecordsService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MedicalRecordsServiceTest {

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @Autowired
    private DataFileAccess dataFileAccess;

    private List<String> medicationsFromPersonList = MedicalRecordTestData.getMedicationsFromPersonList();

    private List<String> allergiesFromPersonList = MedicalRecordTestData.getAllergiesFromPersonList();

    private List<Person> personList;

    @Before
    public void setup() {
        personList = CommonTestData.getPersonList();

        DataFile dataFileTest = new DataFile(CommonTestData.getPersonList(), CommonTestData.getFirestationsList(), CommonTestData.getMedicalRecordsList());
        ((DataFileAccessImpl) dataFileAccess).setDataFile(dataFileTest);
    }

    @Test
    public void getMedicationFromPersonTest() {
        Assertions.assertThat(medicalRecordsService.getMedicationsFromPerson(personList.get(0))).isEqualTo(medicationsFromPersonList);
    }

    @Test
    public void getAllergiesFromPersonTest() {
        Assertions.assertThat(medicalRecordsService.getAllergiesFromPerson(personList.get(0))).isEqualTo(allergiesFromPersonList);
    }

    @Test
    public void saveMedicalRecordsTest() {
        Assertions.assertThat(medicalRecordsService.saveMedicalRecords(CommonTestData.getMedicalRecordsToAddTest())).isEqualTo(CommonTestData.getMedicalRecordsToAddTest());
    }

    @Test
    public void updateMedicalRecordsTest() {
        Assertions.assertThat(medicalRecordsService.updateMedicalRecords(CommonTestData.getMedicalRecordsToUpdateTest())).isEqualTo(CommonTestData.getMedicalRecordsToUpdateTest());

    }

    @Test
    public void deleteMedicalRecordsTest() {
        Assertions.assertThat(medicalRecordsService.deleteMedicalRecords(CommonTestData.getMedicalRecordsToDeleteTest())).isEqualTo(true);
    }
}
