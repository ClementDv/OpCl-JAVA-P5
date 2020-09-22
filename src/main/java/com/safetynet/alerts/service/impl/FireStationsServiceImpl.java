package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.FireStationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationsServiceImpl implements FireStationsService {

    @Autowired
    private DataFileAccess dataFileAccess;

    @Override
    public int getNbStationByAddressFromPerson(Person person) {
        return dataFileAccess.getFirestations()
                .stream()
                .filter(fireStation -> person.getAddress().compareTo(fireStation.getAddress()) == 0)
                .findFirst()
                .map(Firestations::getStation)
                .orElse(0);
    }

    @Override
    public Firestations saveFirestation(Firestations model, List<Firestations> editList) {
        boolean i = true;
        if (editList != null) {
            for (Firestations firestations : editList) {
                if (firestations.equals(model)) {
                    i = false;
                    break;
                }
            }
            if (i == true) {
                editList.add(model);
                //logger.info("Firestation added");
                return model;
            }
        } else {
            editList.add(model);
            //logger.info("Firestation added");
            return model;
        }
        //logger.info("Firestation couldn't be added");
        return null;
    }

    @Override
    public void deleteFirestation(Firestations model, List<Firestations> editList) {
        boolean i = false;

        if (editList != null) {
            for (Firestations firestations : editList) {
                if (firestations.equals(model)) {
                    editList.remove(firestations);
                    //logger.info("Firestation removed");
                    i = true;
                    break;
                }
            }
            if (i == false) {
                //logger.info("Firestation couldn't be removed");
            }
        }
    }
}
