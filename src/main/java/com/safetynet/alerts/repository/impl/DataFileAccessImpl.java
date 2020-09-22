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
    @Override
    public int getStationByAddressFromPerson(Person person) {
        return 0;
    }

    public ArrayList<Integer> getStationByAddress(String address) {
        ArrayList<Integer> stationNumber = new ArrayList<Integer>();

        for (Firestations fireStation : dataFile.getFirestations()) {
            if (address.compareTo(fireStation.getAddress()) == 0) {
                stationNumber.add(fireStation.getStation());
            }
        }
        return stationNumber;
    }





    public ArrayList<String> getMedicationsFromPerson(Person person) {
        for (MedicalRecords medicalRecords : dataFile.getMedicalrecords()) {
            if (person.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                    person.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                return medicalRecords.getMedications();
            }
        }
        return null;
    }

    public ArrayList<String> getAllergiesFromPerson(Person person) {
        for (MedicalRecords medicalRecords : dataFile.getMedicalrecords()) {
            if (person.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                    person.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                return medicalRecords.getAllergies();
            }
        }
        return null;
    }

    @Override
    public List<Firestations> getFirestations() {
        return dataFile.getFirestations();
    }
}
