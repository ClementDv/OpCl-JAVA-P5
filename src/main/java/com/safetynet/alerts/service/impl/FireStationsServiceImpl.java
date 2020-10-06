package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.exception.ControllerAdvisor;
import com.safetynet.alerts.exception.firestation.NoFirestationFoundException;
import com.safetynet.alerts.exception.person.NoPersonFoundFromAddressException;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.*;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.FireStationsService;
import com.safetynet.alerts.service.MedicalRecordsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationsServiceImpl implements FireStationsService {

    private static final Logger log = LogManager.getLogger(ControllerAdvisor.class);

    @Autowired
    private DataFileAccess dataFileAccess;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @Override
    public FirestationsZone getFirestationZone(int firestationNumber) {
        List<Person> personList = dataFileAccess.getPersonsByFirestationNumber(firestationNumber);
        long nbChildren = personList.stream().filter(p -> dataFileAccess.getAgeFromPerson(p) <= 18).count();
        long nbAdults = personList.stream().filter(p -> dataFileAccess.getAgeFromPerson(p) > 18).count();
        if (CollectionUtils.isNotEmpty(personList)) {
            log.info("Request get firestation zone successful!");
            return new FirestationsZone(personList, nbAdults, nbChildren);
        }
        log.info("Request get firestation zone failed.");
        throw new NoFirestationFoundException(List.of(firestationNumber));
    }

    @Override
    public List<String> getPhoneAlertFromFirestations(int firestationNumber) {
        List<String> phonenumberList = new ArrayList<>();

        for (Person person : dataFileAccess.getPersons()) {
            if (dataFileAccess.getNbStationByAddressFromPerson(person) == firestationNumber) {
                phonenumberList.add(person.getPhone());
            }
        }
        if (CollectionUtils.isNotEmpty(phonenumberList)) {
            log.info("Request get phone alert successful!");
            return phonenumberList;
        }
        log.info("Request get phone alert failed.");
        throw new NoFirestationFoundException(List.of(firestationNumber));
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
            if (person.getAddress().equals(address)) {
                fireInfoPerson.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                        null, null, null, person.getPhone(), null, null,
                        dataFileAccess.getAgeFromPerson(person),
                        medicalRecordsService.getMedicationsFromPerson(person),
                        medicalRecordsService.getAllergiesFromPerson(person), 0));
            }
        }
        if (CollectionUtils.isNotEmpty(fireInfoPerson)) {
            log.info("Request get person information with address successful!");
            return new FireMedicalRecord(stationNumber, fireInfoPerson);
        }
        log.info("Request get person information with address failed.");
        throw new NoPersonFoundFromAddressException(address);
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
        List<Integer> nbEmptyStationList;
        if ((nbEmptyStationList = checkEmptyStation(infoByStationList)) == null) {
            log.info("Request get person information with station list successful!");
            return infoByStationList;
        }
        log.info("Request get person information with station list failed.");
        throw new NoFirestationFoundException(nbEmptyStationList);
    }

    private List<Integer> checkEmptyStation(List<InfoByStation> infoByStationList) {
        List<Integer> nbEmptyStationList = new ArrayList<>();

        if (infoByStationList != null) {
            for (InfoByStation infoByStation : infoByStationList) {
                if (CollectionUtils.isEmpty(infoByStation.getListInfo())) {
                    nbEmptyStationList.add(infoByStation.getStation());
                }
            }
            if (CollectionUtils.isNotEmpty(nbEmptyStationList)) return nbEmptyStationList;
        }
        return null;
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
        Firestations result = dataFileAccess.saveFirestation(model);
        if (result != null) log.info("Request save firestation successful!");
        log.info("Request save firestation failed.");
        return result;
    }

    @Override
    public boolean deleteFirestation(Firestations model) {
        boolean result = dataFileAccess.deleteFirestation(model);
        if (result) log.info("Request delete firestation successful!");
        log.info("Request delete firestation failed.");
        return result;
    }
}
