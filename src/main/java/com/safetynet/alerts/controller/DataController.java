package com.safetynet.alerts.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.*;
import com.safetynet.alerts.service.DataFileAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    private DataFileAccess dataFileAccess;

    public DataController(@Autowired DataFileAccess dataFileAccess) {
        this.dataFileAccess = dataFileAccess;
    }


    @JsonView(View.FirestationById.class)
    @GetMapping(value = "/firestation", produces = "application/json")
    public FirestationsZone getFirestationsByID(@RequestParam int stationNumber) {
        ArrayList<Person> personList = new ArrayList<Person>();
        int nbChildren = 0;
        int nbAdults = 0;

        for (Person person : dataFileAccess.dataFile.getPersons()) {
            if (dataFileAccess.getStationByAddressFromPerson(person) == stationNumber) {
                personList.add(person);
                if (dataFileAccess.getAgeFromPerson(person) > 18) {
                    nbAdults++;
                } else nbChildren++;
            }
        }
        if (personList.size() == 0) return null;
        FirestationsZone firestationsZone = new FirestationsZone(personList, nbAdults, nbChildren);
        return firestationsZone;
    }

    @JsonView(View.FilterChildAlertEndpoints.class)
    @GetMapping(value = "/childAlert", produces = "application/json")
    public ChildAlert getChildAlertFromAddress(@RequestParam String address) {
        ArrayList<FullInfoPerson> listChild = new ArrayList<FullInfoPerson>();
        ArrayList<FullInfoPerson> listAdult = new ArrayList<FullInfoPerson>();
        int age;

        for (Person person : dataFileAccess.dataFile.getPersons()) {
            if (person.getAddress().compareTo(address) == 0) {
                if ((age = dataFileAccess.getAgeFromPerson(person)) < 19) {
                    listChild.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                            null, null, null, null, null, null,
                            age, null, null, 0));
                } else {
                    listAdult.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                            null, null, null, null, null, null,
                            age, null, null, 0));
                }
            }
        }
        if (listChild.size() == 0) return null;
        ChildAlert childAlert = new ChildAlert(address, listChild, listAdult);
        return childAlert;
    }

    @JsonView(View.Phone.class)
    @GetMapping(value = "phoneAlert", produces = "application/json")
    public ArrayList<Person> getPhoneAlertFromFirestations(@RequestParam int firestation) {
        ArrayList<Person> listPerson = new ArrayList<Person>();

        for (Person person : dataFileAccess.dataFile.getPersons()) {
            if (dataFileAccess.getStationByAddressFromPerson(person) == firestation) {
                listPerson.add(person);
            }
        }
        if (listPerson.size() == 0) return null;
        return listPerson;
    }

    @JsonView(View.FilterFireEndpoints.class)
    @GetMapping(value = "fire", produces = "application/json")
    public FireMedicalRecord getPersonInfosByAddressIfFire(@RequestParam String address) {
        ArrayList<FullInfoPerson> fireInfoPerson = new ArrayList<FullInfoPerson>();
        ArrayList<Integer> stationNumber = dataFileAccess.getStationByAddress(address);

        if (stationNumber.size() == 0) return null;
        for (Person person : dataFileAccess.dataFile.getPersons()) {
            if (person.getAddress().compareTo(address) == 0) {
                fireInfoPerson.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                        null, null, null, person.getPhone(), null, null,
                        dataFileAccess.getAgeFromPerson(person),
                        dataFileAccess.getMedicationsFromPerson(person),
                        dataFileAccess.getAllergiesFromPerson(person), 0));
            }
        }
        FireMedicalRecord fireMedicalRecord = new FireMedicalRecord(stationNumber, fireInfoPerson);
        return fireMedicalRecord;
    }

    private static boolean isPartOfStation(int station, ArrayList<Integer> stationArr) {
        for (Integer stationNumber : stationArr) {
            if (station == stationNumber)
                return true;
        }
        return false;
    }

    @JsonView(View.FilterFloodStations.class)
    @GetMapping(value = "flood/stations", produces = "application/json")
    public ArrayList<InfoByStation> getPersonInfoByStationsList(@RequestParam List<Integer> stations) {
        ArrayList<InfoByStation> infoByStationList = new ArrayList<InfoByStation>();
        ArrayList<Integer> stationArr = new ArrayList<Integer>();
        int stationCounterRequest = 0;

        if (stations != null) {
            for (int stationNumber : stations) {
                ArrayList<InfoByAddress> infoByAddressList = new ArrayList<InfoByAddress>();
                int index;
                for (Person person : dataFileAccess.dataFile.getPersons()) {
                    stationArr = dataFileAccess.getStationByAddress(person.getAddress());
                    if (isPartOfStation(stations.get(stationCounterRequest), stationArr)) {
                        if ((index = InfoByAddressAlreadyExist(infoByAddressList, person)) == -1) {
                            ArrayList<FullInfoPerson> fullInfoPersonList = new ArrayList<FullInfoPerson>();
                            fullInfoPersonList.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                                    null, null, null, person.getPhone(), null,
                                    null, dataFileAccess.getAgeFromPerson(person),
                                    dataFileAccess.getMedicationsFromPerson(person),
                                    dataFileAccess.getAllergiesFromPerson(person), 0));
                            infoByAddressList.add(new InfoByAddress(person.getAddress(), fullInfoPersonList));
                        } else {
                            InfoByAddress infoByAddress = infoByAddressList.get(index);
                            infoByAddress.addPerson(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                                    null, null, null, person.getPhone(), null,
                                    null, dataFileAccess.getAgeFromPerson(person),
                                    dataFileAccess.getMedicationsFromPerson(person),
                                    dataFileAccess.getAllergiesFromPerson(person), 0));
                        }

                    }
                }
                stationCounterRequest++;
                infoByStationList.add(new InfoByStation(infoByAddressList, stationNumber));
            }
        }
        if (infoByStationList.size() == 0) return null;
        return infoByStationList;
    }

    private static int InfoByAddressAlreadyExist(ArrayList<InfoByAddress> infoByAddressList, Person person) {
        if (infoByAddressList.size() != 0) {
            for (InfoByAddress infoByAddress : infoByAddressList) {
                if (infoByAddress.getAddress().compareTo(person.getAddress()) == 0) {
                    return infoByAddressList.indexOf(infoByAddress);
                }
            }
        }
        return -1;
    }

    @JsonView(View.FilterFullInfoByName.class)
    @GetMapping(value = "personInfo", produces = "application/json")
    public ArrayList<FullInfoPerson> getFullInfoByName(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = true) String lastName) {
        ArrayList<FullInfoPerson> fullInfoPersonList = new ArrayList<FullInfoPerson>();
        for (Person person : dataFileAccess.dataFile.getPersons()) {
            if (firstName != null) {
                if (firstName.compareTo(person.getFirstName()) == 0 && lastName.compareTo(person.getLastName()) == 0) {
                    fullInfoPersonList.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                            person.getAddress(), person.getCity(), person.getZip(), null, person.getEmail(),
                            null, dataFileAccess.getAgeFromPerson(person), dataFileAccess.getMedicationsFromPerson(person),
                            dataFileAccess.getAllergiesFromPerson(person), 0));
                }
            } else {
                if (lastName.compareTo(person.getLastName()) == 0) {
                    fullInfoPersonList.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                            person.getAddress(), person.getCity(), person.getZip(), null, person.getEmail(),
                            null, dataFileAccess.getAgeFromPerson(person), dataFileAccess.getMedicationsFromPerson(person),
                            dataFileAccess.getAllergiesFromPerson(person), 0));
                }
            }
        }
        if (fullInfoPersonList.size() == 0) return null;
        return fullInfoPersonList;
    }

    @JsonView(View.FilterEmailPerson.class)
    @GetMapping(value = "communityEmail", produces = "application/json")
    public ArrayList<Person> getEmailsFromCity(@RequestParam String city) {
        ArrayList<Person> listPerson = new ArrayList<Person>();

        for (Person person : dataFileAccess.dataFile.getPersons()) {
            if (person.getCity().compareTo(city) == 0) {
                listPerson.add(person);
            }
        }
        if (listPerson.size() == 0) return null;
        return listPerson;
    }

    @PostMapping(
            value = "/person", consumes = "application/json", produces = "application/json")
    public Person createPerson(@RequestBody Person person) {
        return dataFileAccess.savePerson(person, dataFileAccess.dataFile.getPersons());
    }

    @PutMapping(
            value = "/person", consumes = "application/json", produces = "application/json")
    public Person updatePerson(@RequestBody Person person) {
        return dataFileAccess.updatePerson(person, dataFileAccess.dataFile.getPersons());
    }

    @DeleteMapping(
            value = "/person", consumes = "application/json")
    public void deletePerson(@RequestBody Person person) {
        dataFileAccess.deletePerson(person, dataFileAccess.dataFile.getPersons());
    }

    @PostMapping(
            value = "/medicalRecords", consumes = "application/json", produces = "application/json")
    public MedicalRecords createMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        return dataFileAccess.saveMedicalRecords(medicalRecords, dataFileAccess.dataFile.getMedicalrecords());
    }

    @PutMapping(
            value = "/medicalRecords", consumes = "application/json", produces = "application/json")
    public MedicalRecords updateMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        return dataFileAccess.updateMedicalRecords(medicalRecords, dataFileAccess.dataFile.getMedicalrecords());
    }

    @DeleteMapping(
            value = "/medicalRecords", consumes = "application/json")
    public void deleteMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        dataFileAccess.deleteMedicalRecords(medicalRecords, dataFileAccess.dataFile.getMedicalrecords());
    }

    @PostMapping(
            value = "/firestation", consumes = "application/json", produces = "application/json")
    public Firestations saveFirestation(@RequestBody Firestations firestations) {
        return dataFileAccess.saveFirestation(firestations, dataFileAccess.dataFile.getFirestations());
    }

    @DeleteMapping(
            value = "/firestation", consumes = "application/json")
    public void deleteFirestation (@RequestBody Firestations firestations) {
        dataFileAccess.deleteFirestation(firestations, dataFileAccess.dataFile.getFirestations());
    }

}
