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
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Repository
public class DataFileAccessImpl implements DataFileAccess {

    private static final Logger logger = LogManager.getLogger(DataFileAccessImpl.class);

    private ObjectMapper objectMapper;
    private DataFile dataFile;

    @Autowired
    public DataFileAccessImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }

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
        if (person != null) {
            loadDataFile();
            return getFirestations()
                    .stream()
                    .filter(fireStation -> person.getAddress().equals(fireStation.getAddress()))
                    .findFirst()
                    .map(Firestations::getStation)
                    .orElse(0);
        }
        return 0;
    }

    @Override
    public int getAgeFromPerson(Person person) {
        if (person != null) {
            for (MedicalRecords medicalRecords : loadDataFile().getMedicalrecords()) {
                if (Objects.equals(person.getFirstName(), medicalRecords.getFirstName()) &&
                        Objects.equals(person.getLastName(), medicalRecords.getLastName())) {
                    return getAgeFromBirthdate(medicalRecords.getBirthdate());
                }
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
        if (address != null) {


            for (Person person : loadDataFile().getPersons()) {
                if (person.getAddress().equals(address)) {
                    result.add(person);
                }
            }
            return result;
        }
        return result;
    }

    @Override
    public int getAgeFromBirthdate(String birthdate) {
        LocalDate currentDate = LocalDate.now();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter = formatter.withLocale(Locale.FRANCE);
            LocalDate birthDate = LocalDate.parse(birthdate, formatter);
            return Period.between(birthDate, currentDate).getYears();
        } catch (DateTimeParseException e) {
            logger.info("Birthdate non valid.");
        } catch (RuntimeException e) {
            logger.info("Birthdate non valid.");
        }
        return 0;
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
        if (model != null) {
            boolean i;
            if (loadDataFile().getPersons() != null) {
                i = loadDataFile().getPersons().stream().noneMatch(person -> person.equals(model));
                if (i) {
                    loadDataFile().getPersons().add(model);
                    return model;
                }
            } else {
                List<Person> personList = new ArrayList<>();
                personList.add(model);
                loadDataFile().setPersons(personList);
                return model;

            }
        }
        return null;
    }

    @Override
    public Person updatePerson(Person model) {
        if (model != null) {
            if (loadDataFile().getPersons() != null) {
                Optional<Person> personOptionalToUpdate = loadDataFile().getPersons().stream().filter(person -> model.getFirstName().equals(person.getFirstName()) &&
                        model.getLastName().equals(person.getLastName())).findFirst();
                if (personOptionalToUpdate.isPresent()) {
                    loadDataFile().getPersons().set(loadDataFile().getPersons().indexOf(personOptionalToUpdate.get()), model);
                    return model;
                }
            } else loadDataFile().setPersons(new ArrayList<>());
        }
        return null;
    }

    @Override
    public boolean deletePerson(Person model) {
        if (model != null) {
            if (loadDataFile().getPersons() != null) {
                Optional<Person> personOptionalTodelete = loadDataFile().getPersons().stream().filter(person -> model.getFirstName().equals(person.getFirstName()) &&
                        model.getLastName().equals(person.getLastName())).findFirst();
                if (personOptionalTodelete.isPresent()) {
                    loadDataFile().getPersons().remove(personOptionalTodelete.get());
                    return true;
                }
            } else loadDataFile().setPersons(new ArrayList<>());
        }
        return false;
    }

    @Override
    public MedicalRecords saveMedicalRecords(MedicalRecords model) {
        if (model != null) {
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
        }
        return null;
    }

    @Override
    public MedicalRecords updateMedicalRecords(MedicalRecords model) {
        if (model != null) {
            if (loadDataFile().getMedicalrecords() != null) {
                Optional<MedicalRecords> medicalRecordsOptionalToUpdate = loadDataFile().getMedicalrecords().stream().filter(medicalRecords -> model.getFirstName().equals(medicalRecords.getFirstName()) &&
                        model.getLastName().equals(medicalRecords.getLastName())).findFirst();
                if (medicalRecordsOptionalToUpdate.isPresent()) {
                    loadDataFile().getMedicalrecords().set(loadDataFile().getMedicalrecords().indexOf(medicalRecordsOptionalToUpdate.get()), model);
                    return model;
                }
            } else loadDataFile().setMedicalrecords(new ArrayList<>());
        }
        return null;
    }

    @Override
    public boolean deleteMedicalRecords(MedicalRecords model) {
        if (model != null) {
            if (loadDataFile().getMedicalrecords() != null) {
                Optional<MedicalRecords> medicalRecordsOptionalToUpdate = loadDataFile().getMedicalrecords().stream().filter(medicalRecords -> model.getFirstName().equals(medicalRecords.getFirstName()) &&
                        model.getLastName().equals(medicalRecords.getLastName())).findFirst();
                if (medicalRecordsOptionalToUpdate.isPresent()) {
                    loadDataFile().getMedicalrecords().remove(medicalRecordsOptionalToUpdate.get());
                    return true;
                }
            } else loadDataFile().setMedicalrecords(new ArrayList<>());
        }
        return false;
    }

    @Override
    public Firestations saveFirestation(Firestations model) {
        if (model != null) {
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
        }
        return null;
    }

    @Override
    public boolean deleteFirestation(Firestations model) {
        if (model != null) {
            if (loadDataFile().getFirestations() != null) {
                return loadDataFile().getFirestations().remove(model);
            } else loadDataFile().setFirestations(new ArrayList<>());
        }
        return false;
    }
}
