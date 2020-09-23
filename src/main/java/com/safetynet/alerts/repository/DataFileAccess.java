package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;

import java.util.ArrayList;
import java.util.List;

public interface DataFileAccess {
    // todo: DataController -> FireStationsService -> DataFileAcces Le controller devrait appeler la mthode depis le serice)

    List<Firestations> getFirestations();

    List<Person> getPersonsByFirestationNumber(int firestationNumber);

    int getAgeFromPerson(Person person);

    List<Person> getPersonsByAddress(String address);

    List<Person> getPersons();

    List<MedicalRecords> getMedicalrecords();
}
