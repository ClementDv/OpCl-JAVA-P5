package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.FireMedicalRecord;
import com.safetynet.alerts.model.specific.FirestationsZone;
import com.safetynet.alerts.model.specific.InfoByStation;

import java.util.List;

public interface FireStationsService {

    FirestationsZone getFirestationZone(int firestationNumber);

    List<Person> getPhoneAlertFromFirestations(int firestation);

    List<Integer> getStationByAddress(String address);

    FireMedicalRecord getPersonInfosByAddressIfFire(String address);

    List<InfoByStation> getPersonInfoByStationsList(List<Integer> stations);

    Firestations saveFirestation(Firestations model, List<Firestations> editList);

    void deleteFirestation(Firestations model, List<Firestations> editList);
}
