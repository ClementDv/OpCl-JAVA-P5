package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

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
        return dataFileAccess.saveMedicalRecords(model);
    }

    @Override
    public MedicalRecords updateMedicalRecords(MedicalRecords model) {
       return dataFileAccess.updateMedicalRecords(model);
    }

    @Override
    public void deleteMedicalRecords(MedicalRecords model) {
       dataFileAccess.deleteMedicalRecords(model);
    }
}
