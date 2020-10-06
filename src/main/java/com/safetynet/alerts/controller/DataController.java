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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    private FireStationsService fireStationsService;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @Autowired
    private PersonsService personsService;

    @JsonView(View.FirestationById.class)
    @GetMapping(value = "/firestation", produces = "application/json")
    public FirestationsZone getFirestationsByID(@RequestParam int stationNumber) {
        return fireStationsService.getFirestationZone(stationNumber);
    }

    @JsonView(View.FilterChildAlertEndpoints.class)
    @GetMapping(value = "/childAlert", produces = "application/json")
    public ChildAlert getChildAlertFromAddress(@RequestParam String address) {
        return personsService.getChildAlertFromAddress(address);
    }

    @JsonView(View.Phone.class)
    @GetMapping(value = "phoneAlert", produces = "application/json")
    public List<String> getPhoneAlertFromFirestations(@RequestParam int firestation) {
        return fireStationsService.getPhoneAlertFromFirestations(firestation);
    }

    @JsonView(View.FilterFireEndpoints.class)
    @GetMapping(value = "fire", produces = "application/json")
    public FireMedicalRecord getPersonInfosByAddressIfFire(@RequestParam String address) {
        return fireStationsService.getPersonInfosByAddressIfFire(address);
    }


    @JsonView(View.FilterFloodStations.class)
    @GetMapping(value = "flood/stations", produces = "application/json")
    public List<InfoByStation> getPersonInfoByStationsList(@RequestParam List<Integer> stations) {
        return fireStationsService.getPersonInfoByStationsList(stations);
    }


    @JsonView(View.FilterFullInfoByName.class)
    @GetMapping(value = "personInfo", produces = "application/json")
    public List<FullInfoPerson> getFullInfoByName(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName") String lastName) {
        return personsService.getFullInfoByName(firstName, lastName);
    }

    @GetMapping(value = "communityEmail", produces = "application/json")
    public List<String> getEmailsFromCity(@RequestParam String city) {
        return personsService.getEmailsFromCity(city);
    }

    @PostMapping(
            value = "/person", consumes = "application/json", produces = "application/json")
    public Person createPerson(@RequestBody Person person) {
        return personsService.savePerson(person);
    }

    @PutMapping(
            value = "/person", consumes = "application/json", produces = "application/json")
    public Person updatePerson(@RequestBody Person person) {
        return personsService.updatePerson(person);
    }

    @DeleteMapping(
            value = "/person", consumes = "application/json")
    public boolean deletePerson(@RequestBody Person person) {
        return personsService.deletePerson(person);
    }

    @PostMapping(
            value = "/medicalRecords", consumes = "application/json", produces = "application/json")
    public MedicalRecords createMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        return medicalRecordsService.saveMedicalRecords(medicalRecords);
    }

    @PutMapping(
            value = "/medicalRecords", consumes = "application/json", produces = "application/json")
    public MedicalRecords updateMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        return medicalRecordsService.updateMedicalRecords(medicalRecords);
    }

    @DeleteMapping(
            value = "/medicalRecords", consumes = "application/json")
    public boolean deleteMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        return medicalRecordsService.deleteMedicalRecords(medicalRecords);
    }

    @PostMapping(
            value = "/firestation", consumes = "application/json", produces = "application/json")
    public Firestations saveFirestation(@RequestBody Firestations firestations) {
        return fireStationsService.saveFirestation(firestations);
    }

    @DeleteMapping(
            value = "/firestation", consumes = "application/json")
    public boolean deleteFirestation(@RequestBody Firestations firestations) {
        return fireStationsService.deleteFirestation(firestations);
    }
}
