package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.exception.ControllerAdvisor;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.MedicalRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

    private static final Logger log = LogManager.getLogger(ControllerAdvisor.class);

    @Autowired
    private DataFileAccess dataFileAccess;

    @Override
    public List<String> getMedicationsFromPerson(Person person) {
        for (MedicalRecords medicalRecords : dataFileAccess.getMedicalrecords()) {
            if (person.getFirstName().equals(medicalRecords.getFirstName()) &&
                    person.getLastName().equals(medicalRecords.getLastName())) {
                return medicalRecords.getMedications();
            }
        }
        return null;
    }

    @Override
    public List<String> getAllergiesFromPerson(Person person) {
        for (MedicalRecords medicalRecords : dataFileAccess.getMedicalrecords()) {
            if (person.getFirstName().equals(medicalRecords.getFirstName()) &&
                    person.getLastName().equals(medicalRecords.getLastName())) {
                return medicalRecords.getAllergies();
            }
        }
        return null;
    }


    @Override
    public MedicalRecords saveMedicalRecords(MedicalRecords model) {
        MedicalRecords result = dataFileAccess.saveMedicalRecords(model);
        if (result != null) log.info("Request save medical records successful!");
        log.info("Request save medical records failed.");
        return result;
    }

    @Override
    public MedicalRecords updateMedicalRecords(MedicalRecords model) {
        MedicalRecords result = dataFileAccess.updateMedicalRecords(model);
        if (result != null) log.info("Request update medical records successful!");
        log.info("Request update medical records failed.");
        return result;
    }

    @Override
    public boolean deleteMedicalRecords(MedicalRecords model) {
        boolean result = dataFileAccess.deleteMedicalRecords(model);
        if (result) log.info("Request delete MedicalRecords successful.");
        log.info("Request delete MedicalRecords failed.");
        return result;
    }
}
