package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface DataFileAccess {

    int getNbStationByAddressFromPerson(Person person);

    int getAgeFromPerson(Person person);

    List<Person> getPersonsByFirestationNumber(int firestationNumber);

    List<Person> getPersonsByAddress(String address);

    List<Firestations> getFirestations();

    List<Person> getPersons();

    List<MedicalRecords> getMedicalrecords();
}
