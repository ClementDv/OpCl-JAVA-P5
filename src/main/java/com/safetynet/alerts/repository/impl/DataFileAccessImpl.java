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
import java.util.Objects;

@Repository
public class DataFileAccessImpl implements DataFileAccess {

    private static final Logger logger = LogManager.getLogger(DataFileAccessImpl.class);

    private DataFile dataFile;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public DataFile loadDataFile() {
        if (dataFile != null) {
            return dataFile;
        }

        try {
            dataFile = objectMapper.readValue(new File("datafile.json"), DataFile.class);
            logger.debug("Json correctly mapped!");
        } catch (IOException e) {
            logger.error("Error while JSON mapping!");
            throw new RuntimeException(e);
        }
        return dataFile;
    }

    @Override
    public int getNbStationByAddressFromPerson(Person person) {
        loadDataFile();
        return getFirestations()
                .stream()
                .filter(fireStation -> person.getAddress().equals(fireStation.getAddress()))
                .findFirst()
                .map(Firestations::getStation)
                .orElse(0);
    }

    @Override
    public int getAgeFromPerson(Person person) {
        for (MedicalRecords medicalRecords : loadDataFile().getMedicalrecords()) {
            if (Objects.equals(person.getFirstName(), medicalRecords.getFirstName()) &&
                    Objects.equals(person.getLastName(), medicalRecords.getLastName())) {
                return getAgeFromBirthdate(medicalRecords.getBirthdate());
            }
        }
        return 0;
    }

    @Override
    public List<Person> getPersonsByFirestationNumber(int firestationNumber) {
        List<Person> result = new ArrayList<>();

        for (Person person : loadDataFile().getPersons()) {
            if (getNbStationByAddressFromPerson(person) == firestationNumber) {
                result.add(person);
            }
        }
        return result;
    }

    @Override
    public List<Person> getPersonsByAddress(String address) {
        List<Person> result = new ArrayList<>();

        for (Person person : loadDataFile().getPersons()) {
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
    public List<Firestations> getFirestations() {
        return new ArrayList<>(loadDataFile().getFirestations());
    }

    @Override
    public List<Person> getPersons() {
        return new ArrayList<>(loadDataFile().getPersons());
    }

    @Override
    public List<MedicalRecords> getMedicalrecords() {
        return new ArrayList<>(loadDataFile().getMedicalrecords());
    }

    @Override
    public Person savePerson(Person model) {
        boolean i;
        if (loadDataFile().getPersons() != null) {
            i = loadDataFile().getPersons().stream().noneMatch(person -> person.equals(model));
            if (i) {
                loadDataFile().getPersons().add(model);
                return model;
            }
        }
        return null;
    }

    @Override
    public Person updatePerson(Person model) {
        if (loadDataFile().getPersons() != null) {
            loadDataFile().getPersons().stream().filter(person -> model.getFirstName().compareTo(person.getFirstName()) == 0 &&
                    model.getLastName().compareTo(person.getLastName()) == 0).findFirst().ifPresent(person -> loadDataFile().getPersons().set(loadDataFile().getPersons().indexOf(person), model));
            return model;
        }
        return null;
    }

    @Override
    public boolean deletePerson(Person model) {
        return loadDataFile().getPersons().remove(model);
    }

    @Override
    public MedicalRecords saveMedicalRecords(MedicalRecords model) {
        boolean i;
        if (loadDataFile().getMedicalrecords() != null) {
            i = loadDataFile().getMedicalrecords().stream().noneMatch(medicalRecords -> medicalRecords.equals(model));
            if (i) {
                loadDataFile().getMedicalrecords().add(model);
                return model;
            }
        } else {
            List<MedicalRecords> medicalRecordsList = new ArrayList<>();
            medicalRecordsList.add(model);
            loadDataFile().setMedicalrecords(medicalRecordsList);
            return model;
        }
        return null;
    }

    @Override
    public MedicalRecords updateMedicalRecords(MedicalRecords model) {
        if (loadDataFile().getMedicalrecords() != null) {
            loadDataFile().getMedicalrecords().stream().filter(medicalRecords -> model.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                    model.getLastName().compareTo(medicalRecords.getLastName()) == 0).findFirst().ifPresent(medicalRecords -> loadDataFile().getMedicalrecords().set(loadDataFile().getMedicalrecords().indexOf(medicalRecords), model));
            return model;
        }
        return null;
    }

    @Override
    public boolean deleteMedicalRecords(MedicalRecords model) {
        return loadDataFile().getMedicalrecords().remove(model);
    }

    @Override
    public Firestations saveFirestation(Firestations model) {
        boolean i;
        if (loadDataFile().getFirestations() != null) {
            i = loadDataFile().getFirestations().stream().noneMatch(firestations -> firestations.equals(model));
            if (i) {
                loadDataFile().getFirestations().add(model);
                return model;
            }
        } else {
            List<Firestations> firestationsList = new ArrayList<>();
            firestationsList.add(model);
            loadDataFile().setFirestations(firestationsList);
            return model;
        }
        return null;
    }

    @Override
    public boolean deleteFirestation(Firestations model) {
        return loadDataFile().getFirestations().remove(model);
    }
}
