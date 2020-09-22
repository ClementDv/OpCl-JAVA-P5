package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;

public interface FireStationsService {
    int getStationByAddressFromPerson(Person person);
}
