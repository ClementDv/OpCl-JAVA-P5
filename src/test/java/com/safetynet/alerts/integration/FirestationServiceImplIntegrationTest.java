package com.safetynet.alerts.integration;


import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.FireStationsService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class FirestationServiceImplIntegrationTest {

    @TestConfiguration
    static class FirestationServiceImplIntegrationTestContextConfiguration {

        @Bean
        public DataFileAccess dataFileAccess() {
            DataFileAccess dataFileAccess = new DataFileAccess() {
                @Override
                public int getNbStationByAddressFromPerson(Person person) {
                    return 0;
                }

                @Override
                public int getAgeFromPerson(Person person) {
                    return 0;
                }

                @Override
                public List<Person> getPersonsByFirestationNumber(int firestationNumber) {
                    return null;
                }

                @Override
                public List<Person> getPersonsByAddress(String address) {
                    return null;
                }

                @Override
                public List<Firestations> getFirestations() {
                    return null;
                }

                @Override
                public List<Person> getPersons() {
                    return null;
                }

                @Override
                public List<MedicalRecords> getMedicalrecords() {
                    return null;
                }

                @Override
                public Person savePerson(Person model) {
                    return null;
                }

                @Override
                public Person updatePerson(Person model) {
                    return null;
                }

                @Override
                public boolean deletePerson(Person model) {
                    return false;
                }

                @Override
                public MedicalRecords saveMedicalRecords(MedicalRecords model) {
                    return null;
                }

                @Override
                public MedicalRecords updateMedicalRecords(MedicalRecords model) {
                    return null;
                }

                @Override
                public boolean deleteMedicalRecords(MedicalRecords model) {
                    return false;
                }

                @Override
                public Firestations saveFirestation(Firestations model) {
                    return null;
                }

                @Override
                public boolean deleteFirestation(Firestations model) {
                    return false;
                }
            };
            return dataFileAccess;
        }
    }

    @Autowired
    private FireStationsService fireStationsService;

    @Autowired
    private DataFileAccess dataFileAccess;


}
