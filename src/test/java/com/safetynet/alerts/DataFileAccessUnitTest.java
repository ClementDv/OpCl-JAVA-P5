package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repositoryTest.DataTestUtils;
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
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DataFileAccessUnitTest {

    @Autowired
    private DataFileAccess dataFileAccess;

    @MockBean
    private ObjectMapper objectMapper;

    public static List<Person> personListByStationNumberAndAddressTest = new ArrayList<>();
    static {
        personListByStationNumberAndAddressTest.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personListByStationNumberAndAddressTest.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"));
        personListByStationNumberAndAddressTest.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    public static Person emptyPerson = DataTestUtils.emptyPerson;

    private static Person personToAddTest = DataTestUtils.personToAddTest;

    private static Person personToUpdateTest = DataTestUtils.personToUpdateTest;

    private static Person personToDeleteTest = DataTestUtils.personToDeleteTest;

    private static Firestations emptyFirestation = DataTestUtils.emptyFirestation;

    private static Firestations firestationToAdd = DataTestUtils.firestationToAdd;

    private static Firestations firestationToDelete = DataTestUtils.firestationToDelete;

    private static MedicalRecords emptyMedicalRecord = DataTestUtils.emptyMedicalRecord;

    private static MedicalRecords medicalRecordsToAddTest = DataTestUtils.medicalRecordsToAddTest;

    private static MedicalRecords medicalRecordsToUpdateTest = DataTestUtils.medicalRecordsToUpdateTest;

    private static MedicalRecords medicalRecordsToDeleteTest = DataTestUtils.medicalRecordsToDeleteTest;

    public static List<Person> personListTest = DataTestUtils.personListTest;

    public static List<Firestations> firestationsListTest = DataTestUtils.firestationsListTest;

    public static List<MedicalRecords> medicalRecordsListTest = DataTestUtils.medicalRecordsListTest;

    public static DataFile dataFileTest = new DataFile(personListTest, firestationsListTest, medicalRecordsListTest);

    @Before
    public void setup() throws IOException {
        Mockito.when(objectMapper.readValue(Mockito.any(File.class), Mockito.eq(DataFile.class))).thenReturn(dataFileTest);
    }

    @Test
    public void loadDataFileMultipleTryTest() {
        for (int i = 0; i < 5; i++) Assertions.assertThat(dataFileAccess.loadDataFile()).isEqualTo(dataFileTest);
    }

    @Test
    public void getNbStationByAddressFromValidPersonTest() {
        Assertions.assertThat(dataFileAccess.getNbStationByAddressFromPerson(personListTest.get(0))).isEqualTo(3);
    }

    @Test
    public void getNbStationByAddressFromNoValidPersonTest() {
        Assertions.assertThat(dataFileAccess.getNbStationByAddressFromPerson(emptyPerson)).isEqualTo(0);
    }

    @Test
    public void getNbStationByAddressFromNullPersonTest() {
        Assertions.assertThat(dataFileAccess.getNbStationByAddressFromPerson(null)).isEqualTo(0);
    }

    @Test
    public void getAgeFromValidPersonTest() {
        Assertions.assertThat(dataFileAccess.getAgeFromPerson(personListTest.get(0))).isEqualTo(36);
    }

    @Test
    public void getAgeFromNoValidPersonTest() {
        Assertions.assertThat(dataFileAccess.getAgeFromPerson(emptyPerson)).isEqualTo(0);
    }

    @Test

    public void getAgeFromNullPersonTest() {
        Assertions.assertThat(dataFileAccess.getAgeFromPerson(null)).isEqualTo(0);
    }

    @Test
    public void getPersonsByValidFirestationNumberTest() {
        Assertions.assertThat(dataFileAccess.getPersonsByFirestationNumber(firestationsListTest.get(0).getStation())).isEqualTo(personListByStationNumberAndAddressTest);
    }

    @Test
    public void getPersonsByNoValidFirestationNumberTest() {
        Assertions.assertThat(dataFileAccess.getPersonsByFirestationNumber(-1)).isEqualTo(Collections.emptyList());
    }

    @Test
    public void getPersonsByValidAddress() {
        Assertions.assertThat(dataFileAccess.getPersonsByAddress("1509 Culver St")).isEqualTo(personListByStationNumberAndAddressTest);
    }

    @Test
    public void getPersonsByNoValidAddress() {
        Assertions.assertThat(dataFileAccess.getPersonsByAddress("11")).isEqualTo(Collections.emptyList());
    }

    @Test
    public void getPersonsByNullAddress() {
        Assertions.assertThat(dataFileAccess.getPersonsByAddress(null)).isEqualTo(Collections.emptyList());
    }

    @Test
    public void getAgeFromValidBirthdate() {
        Assertions.assertThat(dataFileAccess.getAgeFromBirthdate(medicalRecordsListTest.get(0).getBirthdate())).isEqualTo(36);
    }

    @Test
    public void getAgeFromNoValidBirthdate() {
        Assertions.assertThat(dataFileAccess.getAgeFromBirthdate("")).isEqualTo(0);
    }

    @Test
    public void getAgeFromNullBirthdate() {
        Assertions.assertThat(dataFileAccess.getAgeFromBirthdate(null)).isEqualTo(0);
    }

    @Test
    public void getFirestationsTest() {
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
    }

    @Test
    public void getPersonsTest() {
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }

    @Test
    public void getMedicalRecordsTest() {
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
    }

    @Test
    public void savePersonNoEmptyListTest() {
        Assertions.assertThat(dataFileAccess.savePerson(personToAddTest)).isEqualTo(personToAddTest);
        personListTest.add(personToAddTest);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
        personListTest.remove(personToAddTest);
    }

    @Test
    public void saveUniquePersonEmptyListTest() {
        dataFileTest.setPersons(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.savePerson(personToAddTest)).isEqualTo(personToAddTest);
        personListTest.add(personToAddTest);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of(personToAddTest));
        personListTest.remove(personToAddTest);
        dataFileTest.setPersons(personListTest);
    }

    @Test
    public void saveExistingPersonListTest() {
        personListTest.add(personToAddTest);
        Assertions.assertThat(dataFileAccess.savePerson(personToAddTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
        personListTest.remove(personToAddTest);
    }

    @Test
    public void saveNullPersonListTest() {
        Assertions.assertThat(dataFileAccess.savePerson(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }

    @Test
    public void savePersonNullListTest() {
        dataFileTest.setPersons(null);
        Assertions.assertThat(dataFileAccess.savePerson(personToAddTest)).isEqualTo(personToAddTest);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of(personToAddTest));
        dataFileTest.setPersons(personListTest);
    }

    @Test
    public void updateExistingPersonNoEmptyListTest() {
        Assertions.assertThat(dataFileAccess.updatePerson(personToUpdateTest)).isEqualTo(personToUpdateTest);
        personListTest.get(0).setCity("Paris");
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
        personListTest.get(0).setCity("Culver");
    }


    @Test
    public void updatePersonEmptyListTest() {
        dataFileTest.setPersons(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.updatePerson(personToUpdateTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of());
        dataFileTest.setPersons(personListTest);
    }

    @Test
    public void updateNoExistingPersonListTest() {
        Assertions.assertThat(dataFileAccess.updatePerson(emptyPerson)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }

    @Test
    public void updateNullPersonListTest() {
        Assertions.assertThat(dataFileAccess.updatePerson(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }

    @Test
    public void updatePersonNullListTest() {
        dataFileTest.setPersons(null);
        Assertions.assertThat(dataFileAccess.updatePerson(personToUpdateTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of());
        dataFileTest.setPersons(personListTest);
    }

    @Test
    public void deletePersonNoEmptyListTest() {
        Person personRemoved = personListTest.get(0);
        Assertions.assertThat(dataFileAccess.deletePerson(personToDeleteTest)).isEqualTo(true);
        personListTest.remove(personRemoved);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
        personListTest.add(0, personRemoved);
    }

    @Test
    public void deletePersonEmptyListTest() {
        dataFileTest.setPersons(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.deletePerson(personToDeleteTest)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of());
        dataFileTest.setPersons(personListTest);
    }

    @Test
    public void deleteNoExistingPersonListTest() {
        Assertions.assertThat(dataFileAccess.deletePerson(emptyPerson)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }

    @Test
    public void deleteNullPersonListTest() {
        Assertions.assertThat(dataFileAccess.deletePerson(null)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }

    @Test
    public void deletePersonNullListTest() {
        dataFileTest.setPersons(null);
        Assertions.assertThat(dataFileAccess.deletePerson(personToAddTest)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of());
        dataFileTest.setPersons(personListTest);
    }

    @Test
    public void saveMedicalRecordNoEmptyListTest() {
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(medicalRecordsToAddTest)).isEqualTo(medicalRecordsToAddTest);
        medicalRecordsListTest.add(medicalRecordsToAddTest);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
        personListTest.remove(medicalRecordsToAddTest);
    }

    @Test
    public void saveUniqueMedicalRecordEmptyListTest() {
        dataFileTest.setMedicalrecords(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(medicalRecordsToAddTest)).isEqualTo(medicalRecordsToAddTest);
        medicalRecordsListTest.add(medicalRecordsToAddTest);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of(medicalRecordsToAddTest));
        medicalRecordsListTest.remove(medicalRecordsToAddTest);
        dataFileTest.setMedicalrecords(medicalRecordsListTest);
    }

    @Test
    public void saveExistingMedicalRecordListTest() {
        medicalRecordsListTest.add(medicalRecordsToAddTest);
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(medicalRecordsToAddTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
        medicalRecordsListTest.remove(medicalRecordsToAddTest);
    }

    @Test
    public void saveNullMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
    }

    @Test
    public void saveMedicalRecordNullListTest() {
        dataFileTest.setMedicalrecords(null);
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(medicalRecordsToAddTest)).isEqualTo(medicalRecordsToAddTest);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of(medicalRecordsToAddTest));
        dataFileTest.setMedicalrecords(medicalRecordsListTest);
    }

    @Test
    public void updateExistingMedicalRecordNoEmptyListTest() {
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(medicalRecordsToUpdateTest)).isEqualTo(medicalRecordsToUpdateTest);
        medicalRecordsListTest.get(0).setMedications(List.of("doliprane:1000mg"));
        medicalRecordsListTest.get(0).setAllergies(List.of());
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
        medicalRecordsListTest.get(0).setMedications(List.of("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecordsListTest.get(0).setAllergies(List.of("nillacilan"));
    }

    @Test
    public void updateMedicalRecordEmptyListTest() {
        dataFileTest.setMedicalrecords(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(medicalRecordsToUpdateTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of());
        dataFileTest.setMedicalrecords(medicalRecordsListTest);
    }

    @Test
    public void updateNoExistingMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(emptyMedicalRecord)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);

    }

    @Test
    public void updateNullMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
    }

    @Test
    public void updateMedicalRecordNullListTest() {
        dataFileTest.setMedicalrecords(null);
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(medicalRecordsToUpdateTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of());
        dataFileTest.setMedicalrecords(medicalRecordsListTest);
    }

    @Test
    public void deleteMedicalRecordNoEmptyListTest() {
        MedicalRecords medicalRecordRemoved = medicalRecordsListTest.get(0);
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(medicalRecordsToDeleteTest)).isEqualTo(true);
        medicalRecordsListTest.remove(medicalRecordRemoved);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
        medicalRecordsListTest.add(0, medicalRecordRemoved);
    }

    @Test
    public void deleteMedicalRecordEmptyListTest() {
        dataFileTest.setMedicalrecords(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(medicalRecordsToDeleteTest)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of());
        dataFileTest.setMedicalrecords(medicalRecordsListTest);
    }

    @Test
    public void deleteNoExistingMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(emptyMedicalRecord)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
    }

    @Test
    public void deleteNullMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(null)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
    }

    @Test
    public void deleteMedicalRecordNullListTest() {
        dataFileTest.setMedicalrecords(null);
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(medicalRecordsToDeleteTest)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of());
        dataFileTest.setMedicalrecords(medicalRecordsListTest);
    }

    @Test
    public void saveFirestationNoEmptyListTest() {
        Assertions.assertThat(dataFileAccess.saveFirestation(firestationToAdd)).isEqualTo(firestationToAdd);
        firestationsListTest.add(firestationToAdd);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
        personListTest.remove(firestationToAdd);
    }

    @Test
    public void saveUniqueFirestationEmptyListTest() {
        dataFileTest.setFirestations(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.saveFirestation(firestationToAdd)).isEqualTo(firestationToAdd);
        firestationsListTest.add(firestationToAdd);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(List.of(firestationToAdd));
        firestationsListTest.remove(firestationToAdd);
        dataFileTest.setFirestations(firestationsListTest);
    }

    @Test
    public void saveExistingFirestationListTest() {
        firestationsListTest.add(firestationToAdd);
        Assertions.assertThat(dataFileAccess.saveFirestation(firestationToAdd)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
        firestationsListTest.remove(firestationToAdd);
    }

    @Test
    public void saveNullFirestationListTest() {
        Assertions.assertThat(dataFileAccess.saveFirestation(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
    }

    @Test
    public void saveFirestationNullListTest() {
        dataFileTest.setFirestations(null);
        Assertions.assertThat(dataFileAccess.saveFirestation(firestationToAdd)).isEqualTo(firestationToAdd);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(List.of(firestationToAdd));
        dataFileTest.setFirestations(firestationsListTest);
    }

    @Test
    public void deleteFirestationNoEmptyListTest() {
        Firestations firestationRemoved = firestationsListTest.get(0);
        Assertions.assertThat(dataFileAccess.deleteFirestation(firestationToDelete)).isEqualTo(true);
        firestationsListTest.remove(firestationRemoved);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
        firestationsListTest.add(0, firestationRemoved);
    }

    @Test
    public void deleteFirestationEmptyListTest() {
        dataFileTest.setFirestations(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.deleteFirestation(firestationToDelete)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(List.of());
        dataFileTest.setFirestations(firestationsListTest);
    }

    @Test
    public void deleteNoExistingFirestationListTest() {
        Assertions.assertThat(dataFileAccess.deleteFirestation(emptyFirestation)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
    }

    @Test
    public void deleteNullFirestationListTest() {
        Assertions.assertThat(dataFileAccess.deleteFirestation(null)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
    }

    @Test
    public void deleteFirestationNullListTest() {
        dataFileTest.setFirestations(null);
        Assertions.assertThat(dataFileAccess.deleteFirestation(firestationToDelete)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(List.of());
        dataFileTest.setFirestations(firestationsListTest);
    }
}


