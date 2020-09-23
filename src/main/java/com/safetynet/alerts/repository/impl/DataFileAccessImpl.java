package com.safetynet.alerts.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class DataFileAccessImpl implements DataFileAccess {

    private static final Logger logger = LogManager.getLogger(DataFileAccessImpl.class);

    public DataFile dataFile;

    public DataFileAccessImpl(@Autowired ObjectMapper objectMapper) {
        try {
            this.dataFile = objectMapper.readValue(new File("datafile.json"), DataFile.class);
            logger.debug("Json correctly mapped !");
        } catch (IOException e) {
            logger.error("Error while JSON mapping !");
            throw new RuntimeException(e);
        }
    }

    // todo: DataController -> FireStationsService -> DataFileAcces Le controller devrait appeler la mthode depis le serice)

    public int getStationByAddressFromPerson(Person person) {
        return getFirestations()
                .stream()
                .filter(fireStation -> person.getAddress().equals(fireStation.getAddress()))
                .findFirst()
                .map(Firestations::getStation)
                .orElse(0);
    }

    @Override
    public List<Firestations> getFirestations() {
        return new ArrayList<>(dataFile.getFirestations());
    }

    @Override
    public List<Person> getPersonsByFirestationNumber(int firestationNumber) {
        List<Person> result = new ArrayList<>();

        for (Person person : dataFile.getPersons()) {
            if (getStationByAddressFromPerson(person) == firestationNumber) {
                result.add(person);
            }
        }
        return result;
    }

    @Override
    public int getAgeFromPerson(Person person) {
        int age = 0;

        for (MedicalRecords medicalRecords : dataFile.getMedicalrecords()) {
            if (person.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                    person.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                age = getAgeFromBirthdate(medicalRecords.getBirthdate());
            }
        }
        return age;
    }

    @Override
    public List<Person> getPersonsByAddress(String address) {
        List<Person> result = new ArrayList<>();

        for (Person person : dataFile.getPersons()) {
            if (person.getAddress().equals(address)) {
                result.add(person);
            }
        }
        return result;
    }

    private int getAgeFromBirthdate(String birthdate) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        formatter = formatter.withLocale(Locale.FRANCE);
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public List<Person> getPersons() {
        return new ArrayList<>(dataFile.getPersons());
    }

    @Override
    public List<MedicalRecords> getMedicalrecords() {
        return new ArrayList<>(dataFile.getMedicalrecords());
    }

}
