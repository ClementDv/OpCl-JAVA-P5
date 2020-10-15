package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.*;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.FireStationsService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class FirestationServiceTest {

    @Autowired
    FireStationsService fireStationsService;

    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    DataFileAccess dataFileAccess;

    private static FirestationsZone firestationsZoneTest = new FirestationsZone(List.of(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
            new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"),
            new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com")),
            2,
            1);

    private static List<String> phoneAlertStringListTest = new ArrayList<>(List.of("841-874-6512", "841-874-6544"));


    private static List<FullInfoPerson> fullInfoPersonListFireStationNumberThreeTest = new ArrayList<>();

    static {
        fullInfoPersonListFireStationNumberThreeTest.add(new FullInfoPerson("John", "Boyd", null, null, null, "841-874-6512", null, null, 36, List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"), 0));
        fullInfoPersonListFireStationNumberThreeTest.add(new FullInfoPerson("Jacob", "Boyd", null, null, null, "841-874-6513", null, null, 31, List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), List.of(), 0));
        fullInfoPersonListFireStationNumberThreeTest.add(new FullInfoPerson("Tenley", "Boyd", null, null, null, "841-874-6512", null, null, 8, List.of(), List.of("peanut"), 0));
    }

    private static List<FullInfoPerson> fullInfoPersonListFireStationNumberTwoTest = new ArrayList<>();

    static {
        fullInfoPersonListFireStationNumberTwoTest.add(new FullInfoPerson("Roger", "Boyd", null, null, null, "841-874-6512", null, null, 3, List.of(), List.of(), 0));
        fullInfoPersonListFireStationNumberTwoTest.add(new FullInfoPerson("Felicia", "Boyd", null, null, null, "841-874-6544", null, null, 34, List.of("tetracyclaz:650mg"), List.of("xilliathal"), 0));
    }

    private static FireMedicalRecord fireMedicalRecordTest = new FireMedicalRecord(List.of(3), fullInfoPersonListFireStationNumberThreeTest);


    private static List<InfoByStation> infoByStationsListTest = new ArrayList<>();

    static {
        infoByStationsListTest.add(new InfoByStation(List.of(new InfoByAddress("1509 Culver St", fullInfoPersonListFireStationNumberThreeTest)), 3));
        infoByStationsListTest.add(new InfoByStation(List.of(new InfoByAddress("29 15th St", fullInfoPersonListFireStationNumberTwoTest)), 2));
    }

    public static List<Person> personListTest = DataTestUtils.personListTest;

    public static List<Firestations> firestationsListTest = DataTestUtils.firestationsListTest;

    public static List<MedicalRecords> medicalRecordsListTest = DataTestUtils.medicalRecordsListTest;

    public static DataFile dataFileTest = new DataFile(personListTest, firestationsListTest, medicalRecordsListTest);

    @Before
    public void setup() throws IOException {
        Mockito.when(objectMapper.readValue(Mockito.any(File.class), Mockito.eq(DataFile.class))).thenReturn(dataFileTest);
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

    /*@Test
    public void saveFirestationTest() {
        List<Firestations> firestationsListSave = new ArrayList<>(firestationsListTest);
        Assertions.assertThat(fireStationsService.saveFirestation(DataTestUtils.firestationToAdd)).isEqualTo(DataTestUtils.firestationToAdd);
        dataFileAccess.loadDataFile().setFirestations(firestationsListSave);
    }*/

    @Test
    public void deletFirestationTest() {
        List<Firestations> firestationsListSave = new ArrayList<>(firestationsListTest);
        Assertions.assertThat(fireStationsService.deleteFirestation(DataTestUtils.firestationToDelete)).isEqualTo(true);
        dataFileAccess.loadDataFile().setFirestations(firestationsListSave);
    }
}

