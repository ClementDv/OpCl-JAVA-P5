package com.safetynet.alerts.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.*;
import com.safetynet.alerts.service.FireStationsService;
import com.safetynet.alerts.service.MedicalRecordsService;
import com.safetynet.alerts.service.PersonsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {
    private static final Logger logger = LogManager.getLogger(DataController.class);

    @Autowired
    private FireStationsService fireStationsService;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @Autowired
    private PersonsService personsService;

    @JsonView(View.FirestationById.class)
    @GetMapping(value = "/firestation", produces = "application/json")
    public FirestationsZone getFirestationsByID(@RequestParam int stationNumber) {
        FirestationsZone result = fireStationsService.getFirestationZone(stationNumber);
        if (result.getPersons().size() == 0) {
            logger.info("Request success but empty answer");
        }
        else logger.info("Request successful");
        return result;
    }

    @JsonView(View.FilterChildAlertEndpoints.class)
    @GetMapping(value = "/childAlert", produces = "application/json")
    public ChildAlert getChildAlertFromAddress(@RequestParam String address) {
        ChildAlert result = personsService.getChildAlertFromAddress(address);

        logger.info("Request successful");
        return result;
    }

    @JsonView(View.Phone.class)
    @GetMapping(value = "phoneAlert", produces = "application/json")
    public List<Person> getPhoneAlertFromFirestations(@RequestParam int firestation) {
        List<Person> result = fireStationsService.getPhoneAlertFromFirestations(firestation);

        if (result.size() == 0) {
            logger.info("Request success but empty answer");
        }
        logger.info("Request successful");
        return result;
    }

    @JsonView(View.FilterFireEndpoints.class)
    @GetMapping(value = "fire", produces = "application/json")
    public FireMedicalRecord getPersonInfosByAddressIfFire(@RequestParam String address) {
        FireMedicalRecord result = fireStationsService.getPersonInfosByAddressIfFire(address);

        logger.info("Request successful");
        return result;
    }


    @JsonView(View.FilterFloodStations.class)
    @GetMapping(value = "flood/stations", produces = "application/json")
    public List<InfoByStation> getPersonInfoByStationsList(@RequestParam List<Integer> stations) {
        List<InfoByStation> result = fireStationsService.getPersonInfoByStationsList(stations);

        if (result.size() == 0) {
            logger.info("Request success but empty answer");
        }
        logger.info("Request successful");
        return result;
    }


    @JsonView(View.FilterFullInfoByName.class)
    @GetMapping(value = "personInfo", produces = "application/json")
    public List<FullInfoPerson> getFullInfoByName(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName") String lastName) {
        List<FullInfoPerson> result = personsService.getFullInfoByName(firstName, lastName);
        if (result.size() == 0) {
            logger.info("Request success but empty answer");
            return null;
        }
        logger.info("Request successful");
        return result;
    }

    @JsonView(View.FilterEmailPerson.class)
    @GetMapping(value = "communityEmail", produces = "application/json")
    public List<Person> getEmailsFromCity(@RequestParam String city) {
       List<Person> result = personsService.getEmailsFromCity(city);

        if (result.size() == 0) {
            logger.info("Request success but empty answer");
            return null;
        }
        logger.info("Request successful");
        return result;
    }

   /* @PostMapping(
            value = "/person", consumes = "application/json", produces = "application/json")
    public Person createPerson(@RequestBody Person person) {
        logger.info("Request successful");
        return dataFileAccess.savePerson(person, dataFileAccess.dataFile.getPersons());
    }

    @PutMapping(
            value = "/person", consumes = "application/json", produces = "application/json")
    public Person updatePerson(@RequestBody Person person) {
        logger.info("Request successful");
        return dataFileAccess.updatePerson(person, dataFileAccess.dataFile.getPersons());
    }

    @DeleteMapping(
            value = "/person", consumes = "application/json")
    public void deletePerson(@RequestBody Person person) {
        logger.info("Request successful");
        dataFileAccess.deletePerson(person, dataFileAccess.dataFile.getPersons());
    }

    @PostMapping(
            value = "/medicalRecords", consumes = "application/json", produces = "application/json")
    public MedicalRecords createMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        logger.info("Request successful");
        return dataFileAccess.saveMedicalRecords(medicalRecords, dataFileAccess.dataFile.getMedicalrecords());
    }

    @PutMapping(
            value = "/medicalRecords", consumes = "application/json", produces = "application/json")
    public MedicalRecords updateMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        logger.info("Request successful");
        return dataFileAccess.updateMedicalRecords(medicalRecords, dataFileAccess.dataFile.getMedicalrecords());
    }

    @DeleteMapping(
            value = "/medicalRecords", consumes = "application/json")
    public void deleteMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        logger.info("Request successful");
        dataFileAccess.deleteMedicalRecords(medicalRecords, dataFileAccess.dataFile.getMedicalrecords());
    }

    @PostMapping(
            value = "/firestation", consumes = "application/json", produces = "application/json")
    public Firestations saveFirestation(@RequestBody Firestations firestations) {
        logger.info("Request successful");
        return dataFileAccess.saveFirestation(firestations, dataFileAccess.dataFile.getFirestations());
    }

    @DeleteMapping(
            value = "/firestation", consumes = "application/json")
    public void deleteFirestation(@RequestBody Firestations firestations) {
        logger.info("Request successful");
        dataFileAccess.deleteFirestation(firestations, dataFileAccess.dataFile.getFirestations());
    }*/

}
