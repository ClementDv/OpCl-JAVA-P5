package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

public interface DataFileAccess {
    // todo: DataController -> FireStationsService -> DataFileAcces Le controller devrait appeler la mthode depis le serice)
    int getStationByAddressFromPerson(Person person);
}
