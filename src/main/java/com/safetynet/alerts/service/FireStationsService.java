package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface FireStationsService {
    int getNbStationByAddressFromPerson(Person person);

    Firestations saveFirestation(Firestations model, List<Firestations> editList);

    void deleteFirestation(Firestations model, List<Firestations> editList);
}
