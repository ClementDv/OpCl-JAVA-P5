package com.safetynet.alerts;

import com.safetynet.alerts.data.CommonTestData;
import com.safetynet.alerts.data.FirestationTestData;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.specific.FireMedicalRecord;
import com.safetynet.alerts.model.specific.FirestationsZone;
import com.safetynet.alerts.model.specific.InfoByStation;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repository.impl.DataFileAccessImpl;
import com.safetynet.alerts.service.FireStationsService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class FirestationServiceTest {

    @Autowired
    FireStationsService fireStationsService;

    @Autowired
    DataFileAccess dataFileAccess;

    private FirestationsZone firestationsZoneTest = FirestationTestData.getFirestationsZoneTest();

    private List<String> phoneAlertStringListTest = FirestationTestData.getPhoneAlertStringListTest();

    private FireMedicalRecord fireMedicalRecordTest = FirestationTestData.getFireMedicalRecordTest();

    private List<InfoByStation> infoByStationsListTest = FirestationTestData.getInfoByStationsListTest();

    @Before
    public void setup() {
        DataFile dataFileTest = new DataFile(CommonTestData.getPersonList(), CommonTestData.getFirestationsList(), CommonTestData.getMedicalRecordsList());
        ((DataFileAccessImpl) dataFileAccess).setDataFile(dataFileTest);
    }

    @Test
    public void getFirestationZoneTest() {
        Assertions.assertThat(fireStationsService.getFirestationZone(3)).isEqualTo(firestationsZoneTest);
    }

    @Test
    public void getPhoneAlertFromFirestationsTest() {
        Assertions.assertThat(fireStationsService.getPhoneAlertFromFirestations(2)).isEqualTo(phoneAlertStringListTest);
    }

    @Test
    public void getStationByAddressTest() {
        Assertions.assertThat(fireStationsService.getStationByAddress("29 15th St")).isEqualTo(List.of(2));
    }

    @Test
    public void getPersonInfosByAddressIfFireTest() {
        Assertions.assertThat(fireStationsService.getPersonInfosByAddressIfFire("1509 Culver St")).isEqualTo(fireMedicalRecordTest);
    }

    @Test
    public void getPersonInfoByStationsListTest() {
        Assertions.assertThat(fireStationsService.getPersonInfoByStationsList(List.of(3, 2))).isEqualTo(infoByStationsListTest);
    }

    @Test
    public void saveFirestationTest() {
        Assertions.assertThat(fireStationsService.saveFirestation(CommonTestData.getFirestationToAdd())).isEqualTo(CommonTestData.getFirestationToAdd());
    }

    @Test
    public void deleteFirestationTest() {
        Assertions.assertThat(fireStationsService.deleteFirestation(CommonTestData.getFirestationToDelete())).isEqualTo(true);
    }
}

