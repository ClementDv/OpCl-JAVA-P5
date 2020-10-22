package com.safetynet.alerts;

import com.safetynet.alerts.data.CommonTestData;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.repository.impl.DataFileAccessImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DataFileAccessTest {

    @Autowired
    private DataFileAccess dataFileAccess;

    private List<Person> personListByStationNumberAndAddressTest = new ArrayList<>();

    {
        personListByStationNumberAndAddressTest.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personListByStationNumberAndAddressTest.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"));
        personListByStationNumberAndAddressTest.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
    }

    private Person emptyPerson = CommonTestData.getEmptyPerson();

    private Person personToAddTest = CommonTestData.getPersonToAddTest();

    private Person personToUpdateTest = CommonTestData.getPersonToUpdateTest();

    private Person personToDeleteTest = CommonTestData.getPersonToDeleteTest();

    private Firestations emptyFirestation = CommonTestData.getEmptyFirestation();

    private Firestations firestationToAdd = CommonTestData.getFirestationToAdd();

    private Firestations firestationToDelete = CommonTestData.getFirestationToDelete();

    private MedicalRecords emptyMedicalRecord = CommonTestData.getEmptyMedicalRecord();

    private MedicalRecords medicalRecordsToAddTest = CommonTestData.getMedicalRecordsToAddTest();

    private MedicalRecords medicalRecordsToUpdateTest = CommonTestData.getMedicalRecordsToUpdateTest();

    private MedicalRecords medicalRecordsToDeleteTest = CommonTestData.getMedicalRecordsToDeleteTest();

    public List<Person> personList;
    public List<Firestations> firestationsList;
    public List<MedicalRecords> medicalRecordsList;
    public DataFile dataFileTest;

    @Before
    public void setup() {
        personList = CommonTestData.getPersonList();
        firestationsList = CommonTestData.getFirestationsList();
        medicalRecordsList = CommonTestData.getMedicalRecordsList();

        dataFileTest = new DataFile(CommonTestData.getPersonList(), CommonTestData.getFirestationsList(), CommonTestData.getMedicalRecordsList());
        ((DataFileAccessImpl) dataFileAccess).setDataFile(dataFileTest);
    }

    @Test
    public void getNbStationByAddressFromValidPersonTest() {
        Assertions.assertThat(dataFileAccess.getNbStationByAddressFromPerson(personList.get(0))).isEqualTo(3);
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
        Assertions.assertThat(dataFileAccess.getAgeFromPerson(personList.get(0))).isEqualTo(36);
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
        System.out.println(firestationsList + "\n");
        System.out.print(firestationsList.get(0).getStation() + "\n");
        Assertions.assertThat(dataFileAccess.getPersonsByFirestationNumber(firestationsList.get(0).getStation())).isEqualTo(personListByStationNumberAndAddressTest);
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
        Assertions.assertThat(dataFileAccess.getAgeFromBirthdate(medicalRecordsList.get(0).getBirthdate())).isEqualTo(36);
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
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsList);
    }

    @Test
    public void getPersonsTest() {
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personList);
    }

    @Test
    public void getMedicalRecordsTest() {
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsList);
    }

    @Test
    public void savePersonNoEmptyListTest() {
        List<Person> personListTest = new ArrayList<>(personList);
        personListTest.add(personToAddTest);
        Assertions.assertThat(dataFileAccess.savePerson(personToAddTest)).isEqualTo(personToAddTest);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }

    @Test
    public void saveUniquePersonEmptyListTest() {
        dataFileTest.setPersons(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.savePerson(personToAddTest)).isEqualTo(personToAddTest);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of(personToAddTest));
    }

    @Test
    public void saveExistingPersonListTest() {
        Assertions.assertThat(dataFileAccess.savePerson(personList.get(0))).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personList);
    }

    @Test
    public void saveNullPersonListTest() {
        Assertions.assertThat(dataFileAccess.savePerson(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personList);
    }

    @Test
    public void savePersonNullListTest() {
        dataFileTest.setPersons(null);
        Assertions.assertThat(dataFileAccess.savePerson(personToAddTest)).isEqualTo(personToAddTest);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of(personToAddTest));
    }

    @Test
    public void updateExistingPersonNoEmptyListTest() {
        List<Person> personListTest = new ArrayList<>(dataFileAccess.getPersons());
        Assertions.assertThat(dataFileAccess.updatePerson(personToUpdateTest)).isEqualTo(personToUpdateTest);
        personListTest.get(0).setCity("Paris");
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }


    @Test
    public void updatePersonEmptyListTest() {
        dataFileTest.setPersons(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.updatePerson(personToUpdateTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of());
    }

    @Test
    public void updateNoExistingPersonListTest() {
        Assertions.assertThat(dataFileAccess.updatePerson(emptyPerson)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personList);
    }

    @Test
    public void updateNullPersonListTest() {
        Assertions.assertThat(dataFileAccess.updatePerson(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personList);
    }

    @Test
    public void updatePersonNullListTest() {
        dataFileTest.setPersons(null);
        Assertions.assertThat(dataFileAccess.updatePerson(personToUpdateTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of());
    }

    @Test
    public void deletePersonNoEmptyListTest() {
        List<Person> personListTest = new ArrayList<>(dataFileTest.getPersons());
        personListTest.remove(0);
        Assertions.assertThat(dataFileAccess.deletePerson(personToDeleteTest)).isEqualTo(true);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personListTest);
    }

    @Test
    public void deletePersonEmptyListTest() {
        dataFileTest.setPersons(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.deletePerson(personToDeleteTest)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of());
    }

    @Test
    public void deleteNoExistingPersonListTest() {
        Assertions.assertThat(dataFileAccess.deletePerson(emptyPerson)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personList);
    }

    @Test
    public void deleteNullPersonListTest() {
        Assertions.assertThat(dataFileAccess.deletePerson(null)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(personList);
    }

    @Test
    public void deletePersonNullListTest() {
        dataFileTest.setPersons(null);
        Assertions.assertThat(dataFileAccess.deletePerson(personToAddTest)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getPersons()).isEqualTo(List.of());
    }

    @Test
    public void saveMedicalRecordNoEmptyListTest() {
        List<MedicalRecords> medicalRecordsListTest = new ArrayList<>(medicalRecordsList);
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(medicalRecordsToAddTest)).isEqualTo(medicalRecordsToAddTest);
        medicalRecordsListTest.add(medicalRecordsToAddTest);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
    }

    @Test
    public void saveUniqueMedicalRecordEmptyListTest() {
        dataFileTest.setMedicalrecords(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(medicalRecordsToAddTest)).isEqualTo(medicalRecordsToAddTest);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of(medicalRecordsToAddTest));
    }

    @Test
    public void saveExistingMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(medicalRecordsList.get(0))).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsList);
    }

    @Test
    public void saveNullMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsList);
    }

    @Test
    public void saveMedicalRecordNullListTest() {
        dataFileTest.setMedicalrecords(null);
        Assertions.assertThat(dataFileAccess.saveMedicalRecords(medicalRecordsToAddTest)).isEqualTo(medicalRecordsToAddTest);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of(medicalRecordsToAddTest));
    }

    @Test
    public void updateExistingMedicalRecordNoEmptyListTest() {
        List<MedicalRecords> medicalRecordsListTest = new ArrayList<>(dataFileTest.getMedicalrecords());
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(medicalRecordsToUpdateTest)).isEqualTo(medicalRecordsToUpdateTest);
        medicalRecordsListTest.get(0).setMedications(List.of("doliprane:1000mg"));
        medicalRecordsListTest.get(0).setAllergies(List.of());
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
    }

    @Test
    public void updateMedicalRecordEmptyListTest() {
        dataFileTest.setMedicalrecords(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(medicalRecordsToUpdateTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of());
    }

    @Test
    public void updateNoExistingMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(emptyMedicalRecord)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsList);
    }

    @Test
    public void updateNullMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsList);
    }

    @Test
    public void updateMedicalRecordNullListTest() {
        dataFileTest.setMedicalrecords(null);
        Assertions.assertThat(dataFileAccess.updateMedicalRecords(medicalRecordsToUpdateTest)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of());
    }

    @Test
    public void deleteMedicalRecordNoEmptyListTest() {
        List<MedicalRecords> medicalRecordsListTest = CommonTestData.getMedicalRecordsList();
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(medicalRecordsToDeleteTest)).isEqualTo(true);
        medicalRecordsListTest.remove(0);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsListTest);
    }

    @Test
    public void deleteMedicalRecordEmptyListTest() {
        dataFileTest.setMedicalrecords(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(medicalRecordsToDeleteTest)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of());
    }

    @Test
    public void deleteNoExistingMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(emptyMedicalRecord)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsList);
    }

    @Test
    public void deleteNullMedicalRecordListTest() {
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(null)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(medicalRecordsList);
    }

    @Test
    public void deleteMedicalRecordNullListTest() {
        dataFileTest.setMedicalrecords(null);
        Assertions.assertThat(dataFileAccess.deleteMedicalRecords(medicalRecordsToDeleteTest)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getMedicalrecords()).isEqualTo(List.of());
    }

    @Test
    public void saveFirestationNoEmptyListTest() {
        List<Firestations> firestationsListTest = new ArrayList<>(dataFileTest.getFirestations());
        Assertions.assertThat(dataFileAccess.saveFirestation(firestationToAdd)).isEqualTo(firestationToAdd);
        firestationsListTest.add(firestationToAdd);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
    }

    @Test
    public void saveUniqueFirestationEmptyListTest() {
        dataFileTest.setFirestations(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.saveFirestation(firestationToAdd)).isEqualTo(firestationToAdd);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(List.of(firestationToAdd));
    }

    @Test
    public void saveExistingFirestationListTest() {
        Assertions.assertThat(dataFileAccess.saveFirestation(firestationsList.get(0))).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsList);
    }

    @Test
    public void saveNullFirestationListTest() {
        Assertions.assertThat(dataFileAccess.saveFirestation(null)).isEqualTo(null);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsList);
    }

    @Test
    public void saveFirestationNullListTest() {
        dataFileTest.setFirestations(null);
        Assertions.assertThat(dataFileAccess.saveFirestation(firestationToAdd)).isEqualTo(firestationToAdd);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(List.of(firestationToAdd));
    }

    @Test
    public void deleteFirestationNoEmptyListTest() {
        System.out.println(firestationsList);
        List<Firestations> firestationsListTest = new ArrayList<>(dataFileTest.getFirestations());
        System.out.println(firestationsList);
        Assertions.assertThat(dataFileAccess.deleteFirestation(firestationToDelete)).isEqualTo(true);
        System.out.println(firestationsList);
        firestationsListTest.remove(0);
        System.out.println("1" + firestationsList);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsListTest);
    }

    @Test
    public void deleteFirestationEmptyListTest() {
        dataFileTest.setFirestations(new ArrayList<>());
        Assertions.assertThat(dataFileAccess.deleteFirestation(firestationToDelete)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(List.of());
    }

    @Test
    public void deleteNoExistingFirestationListTest() {
        Assertions.assertThat(dataFileAccess.deleteFirestation(emptyFirestation)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsList);
    }

    @Test
    public void deleteNullFirestationListTest() {
        Assertions.assertThat(dataFileAccess.deleteFirestation(null)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(firestationsList);
    }

    @Test
    public void deleteFirestationNullListTest() {
        dataFileTest.setFirestations(null);
        Assertions.assertThat(dataFileAccess.deleteFirestation(firestationToDelete)).isEqualTo(false);
        Assertions.assertThat(dataFileAccess.getFirestations()).isEqualTo(List.of());
    }
}