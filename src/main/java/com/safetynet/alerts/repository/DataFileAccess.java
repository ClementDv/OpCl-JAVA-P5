package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface DataFileAccess {
    // todo: DataController -> FireStationsService -> DataFileAcces Le controller devrait appeler la mthode depis le serice)
    int getStationByAddressFromPerson(Person person);

    List<Firestations> getFirestations();
}
