package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataFileAccess;
import com.safetynet.alerts.service.FireStationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationsServiceImpl implements FireStationsService {

    @Autowired
    private DataFileAccess dataFileAccess;

    @Override
    public int getStationByAddressFromPerson(Person person) {
        return dataFileAccess.getFirestations()
                .stream()
                .filter(fireStation -> person.getAddress().compareTo(fireStation.getAddress()) == 0)
                .findFirst()
                .map(Firestations::getStation)
                .orElse(0);
    }
}
