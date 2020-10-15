package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.ChildAlert;
import com.safetynet.alerts.model.specific.FullInfoPerson;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repositoryTest.DataTestUtils;
import com.safetynet.alerts.service.PersonsService;
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
public class PersonsServiceTest {

    @Autowired
    private PersonsService personsService;

    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    DataFileAccess dataFileAccess;

    public static List<FullInfoPerson> fullInfoPersonListChild = new ArrayList<>();

    static {
        fullInfoPersonListChild.add(new FullInfoPerson("Tenley", "Boyd", null, null, null, null, null, null, 8, null, null, 0));
    }

    public static List<FullInfoPerson> fullInfoPersonListAdult = new ArrayList<>();

    static {
        fullInfoPersonListAdult.add(new FullInfoPerson("John", "Boyd", null, null, null, null, null, null, 36, null, null, 0));
        fullInfoPersonListAdult.add(new FullInfoPerson("Jacob", "Boyd", null, null, null, null, null, null, 31, null, null, 0));
    }

    public static ChildAlert childAlertTest = new ChildAlert("1509 Culver St", fullInfoPersonListChild, fullInfoPersonListAdult);

    public static List<FullInfoPerson> fullInfoPersonByNameList = new ArrayList<>();

    static {
        fullInfoPersonByNameList.add(new FullInfoPerson("John", "Boyd", "1509 Culver St", "Culver", "97451", null, "jaboyd@email.com", null, 36, List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"), 0));
        fullInfoPersonByNameList.add(new FullInfoPerson("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", null, "drk@email.com", null, 31, List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), List.of(), 0));
        fullInfoPersonByNameList.add(new FullInfoPerson("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", null, "tenz@email.com", null, 8, List.of(), List.of("peanut"), 0));
        fullInfoPersonByNameList.add(new FullInfoPerson("Roger", "Boyd", "29 15th St", "Culver", "97451", null, "jaboyd@email.com", null, 3, List.of(), List.of(), 0));
        fullInfoPersonByNameList.add(new FullInfoPerson("Felicia", "Boyd", "29 15th St", "Culver", "97451", null, "jaboyd@email.com", null, 34, List.of("tetracyclaz:650mg"), List.of("xilliathal"), 0));
    }

    public static List<String> emailsFromCityList = new ArrayList(List.of("jaboyd@email.com", "drk@email.com", "tenz@email.com", "jaboyd@email.com", "jaboyd@email.com"));

    public static List<Person> personListTest = DataTestUtils.personListTest;

    public static List<Firestations> firestationsListTest = DataTestUtils.firestationsListTest;

    public static List<MedicalRecords> medicalRecordsListTest = DataTestUtils.medicalRecordsListTest;

    public static DataFile dataFileTest = new DataFile(personListTest, firestationsListTest, medicalRecordsListTest);

    @Before
    public void setup() throws IOException {
        Mockito.when(objectMapper.readValue(Mockito.any(File.class), Mockito.eq(DataFile.class))).thenReturn(dataFileTest);
    }

    @Test
    public void getChildAlertFromAddressTest() {
        Assertions.assertThat(personsService.getChildAlertFromAddress("1509 Culver St")).isEqualTo(childAlertTest);
    }

    @Test
    public void getFullInfoByNameTest() {
        Assertions.assertThat(personsService.getFullInfoByName(null, "Boyd")).isEqualTo(fullInfoPersonByNameList);
    }

    @Test
    public void getEmailsFromCityTest() {
        Assertions.assertThat(personsService.getEmailsFromCity("Culver")).isEqualTo(emailsFromCityList);
    }

    @Test
    public void savePersonTest() {
        List<Person> personListSave = new ArrayList<>(dataFileAccess.loadDataFile().getPersons());
        Assertions.assertThat(personsService.savePerson(DataTestUtils.personToAddTest)).isEqualTo(DataTestUtils.personToAddTest);
        dataFileAccess.loadDataFile().setPersons(personListSave);
    }

    @Test
    public void updatePersonTest() {
        List<Person> personListSave = new ArrayList<>(dataFileAccess.loadDataFile().getPersons());
        Assertions.assertThat(personsService.updatePerson(DataTestUtils.personToUpdateTest)).isEqualTo(DataTestUtils.personToUpdateTest);
        dataFileAccess.loadDataFile().setPersons(personListSave);
    }

    @Test
    public void deletePersonTest() {
        List<Person> personListSave = new ArrayList<>(dataFileAccess.loadDataFile().getPersons());
        Assertions.assertThat(personsService.deletePerson(DataTestUtils.personToDeleteTest)).isEqualTo(true);
        dataFileAccess.loadDataFile().setPersons(personListSave);
    }

}
