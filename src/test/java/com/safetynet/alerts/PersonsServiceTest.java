package com.safetynet.alerts;

import com.safetynet.alerts.data.CommonTestData;
import com.safetynet.alerts.data.PersonTestData;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.specific.ChildAlert;
import com.safetynet.alerts.model.specific.FullInfoPerson;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repository.impl.DataFileAccessImpl;
import com.safetynet.alerts.service.PersonsService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PersonsServiceTest {

    @Autowired
    private PersonsService personsService;

    @Autowired
    DataFileAccess dataFileAccess;

    private ChildAlert childAlertTest = PersonTestData.getChildAlertTest();

    private List<FullInfoPerson> fullInfoPersonByNameList = PersonTestData.getFullInfoPersonByNameList();

    private List<String> emailsFromCityList = PersonTestData.getEmailsFromCityList();

    @Before
    public void setup() {
        DataFile dataFileTest = new DataFile(CommonTestData.getPersonList(), CommonTestData.getFirestationsList(), CommonTestData.getMedicalRecordsList());
        ((DataFileAccessImpl) dataFileAccess).setDataFile(dataFileTest);
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
        Assertions.assertThat(personsService.savePerson(CommonTestData.getPersonToAddTest())).isEqualTo(CommonTestData.getPersonToAddTest());
    }

    @Test
    public void updatePersonTest() {
        Assertions.assertThat(personsService.updatePerson(CommonTestData.getPersonToUpdateTest())).isEqualTo(CommonTestData.getPersonToUpdateTest());
    }

    @Test
    public void deletePersonTest() {
        Assertions.assertThat(personsService.deletePerson(CommonTestData.getPersonToDeleteTest())).isEqualTo(true);
    }
}
