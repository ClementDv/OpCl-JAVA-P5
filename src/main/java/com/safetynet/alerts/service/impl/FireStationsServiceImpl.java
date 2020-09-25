package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.*;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.FireStationsService;
import com.safetynet.alerts.service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationsServiceImpl implements FireStationsService {

    @Autowired
    private DataFileAccess dataFileAccess;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @Override
    public FirestationsZone getFirestationZone(int firestationNumber) {
        List<Person> personList = new ArrayList<>();
        int nbChildren = 0;
        int nbAdults = 0;

        for (Person person : dataFileAccess.getPersonsByFirestationNumber(firestationNumber)) {
            personList.add(person);
            if (dataFileAccess.getAgeFromPerson(person) > 18) {
                nbAdults++;
            } else nbChildren++;
        }
        return new FirestationsZone(personList, nbAdults, nbChildren);
    }

    @Override
    public List<Person> getPhoneAlertFromFirestations(int firestation) {
        ArrayList<Person> listPerson = new ArrayList<>();

        for (Person person : dataFileAccess.getPersons()) {
            if (dataFileAccess.getNbStationByAddressFromPerson(person) == firestation) {
                listPerson.add(person);
            }
        }
        return listPerson;
    }

    @Override
    public List<Integer> getStationByAddress(String address) {
        List<Integer> stationNumber = new ArrayList<>();

        for (Firestations fireStation : dataFileAccess.getFirestations()) {
            if (address.compareTo(fireStation.getAddress()) == 0) {
                stationNumber.add(fireStation.getStation());
            }
        }
        return stationNumber;
    }

    @Override
    public FireMedicalRecord getPersonInfosByAddressIfFire(String address) {
        List<FullInfoPerson> fireInfoPerson = new ArrayList<>();
        List<Integer> stationNumber = getStationByAddress(address);

        for (Person person : dataFileAccess.getPersons()) {
            if (person.getAddress().compareTo(address) == 0) {
                fireInfoPerson.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                        null, null, null, person.getPhone(), null, null,
                        dataFileAccess.getAgeFromPerson(person),
                        medicalRecordsService.getMedicationsFromPerson(person),
                        medicalRecordsService.getAllergiesFromPerson(person), 0));
            }
        }
        return new FireMedicalRecord(stationNumber, fireInfoPerson);
    }

    @Override
    public List<InfoByStation> getPersonInfoByStationsList(List<Integer> stations) {
        List<InfoByStation> infoByStationList = new ArrayList<>();
        int stationCounterRequest = 0;

        if (stations != null) {
            for (int stationNumber : stations) {
                List<InfoByAddress> infoByAddressList = new ArrayList<>();
                int index;

                for (Person person : dataFileAccess.getPersons()) {
                    List<Integer> stationArr = getStationByAddress(person.getAddress());
                    if (isPartOfStation(stations.get(stationCounterRequest), stationArr)) {
                        FullInfoPerson fullInfoPerson = new FullInfoPerson(person.getFirstName(), person.getLastName(),
                                null, null, null, person.getPhone(), null,
                                null, dataFileAccess.getAgeFromPerson(person),
                                medicalRecordsService.getMedicationsFromPerson(person),
                                medicalRecordsService.getAllergiesFromPerson(person), 0);
                        if ((index = InfoByAddressAlreadyExist(infoByAddressList, person)) != -1) {
                            InfoByAddress infoByAddress = infoByAddressList.get(index);
                            infoByAddress.addPerson(fullInfoPerson);
                        } else {
                            List<FullInfoPerson> fullInfoPersonList = new ArrayList<>();
                            fullInfoPersonList.add(fullInfoPerson);
                            infoByAddressList.add(new InfoByAddress(person.getAddress(), fullInfoPersonList));
                        }

                    }
                }
                stationCounterRequest++;
                infoByStationList.add(new InfoByStation(infoByAddressList, stationNumber));
            }
        }
        return infoByStationList;
    }

    private static boolean isPartOfStation(int station, List<Integer> stationArr) {
        for (Integer stationNumber : stationArr) {
            if (station == stationNumber)
                return true;
        }
        return false;
    }

    private static int InfoByAddressAlreadyExist(List<InfoByAddress> infoByAddressList, Person person) {
        if (infoByAddressList.size() != 0) {
            for (InfoByAddress infoByAddress : infoByAddressList) {
                if (infoByAddress.getAddress().equals(person.getAddress())) {
                    return infoByAddressList.indexOf(infoByAddress);
                }
            }
        }
        return -1;
    }

    @Override
    public Firestations saveFirestation(Firestations model) {
       return dataFileAccess.saveFirestation(model);
    }

    @Override
    public void deleteFirestation(Firestations model) {
        dataFileAccess.deleteFirestation(model);
    }
}
