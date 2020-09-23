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
    public MedicalRecords saveMedicalRecords(MedicalRecords model, List<MedicalRecords> editList) {
        boolean i = true;
        if (editList != null) {
            for (MedicalRecords medicalRecords : editList) {
                if (medicalRecords.equals(model)) {
                    i = false;
                    break;
                }
            }
            if (i) {
                editList.add(model);
                //logger.info("MedicalRecords added");
                return model;
            }
        } else {
            if (editList != null) editList.add(model);
            //logger.info("MedicalRecords added");
            return model;
        }
        //logger.info("MedicalRecords couldn't be added");
        return null;
    }

    @Override
    public MedicalRecords updateMedicalRecords(MedicalRecords model, List<MedicalRecords> editList) {
        if (editList != null) {
            for (MedicalRecords medicalRecords : editList) {
                if (model.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                        model.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                    editList.set(editList.indexOf(medicalRecords), model);
                    break;
                }
            }
            //logger.info("MedicalRecords updated");
            return model;
        }
        //logger.info("MedicalRecords couldn't be updated");
        return null;
    }

    @Override
    public void deleteMedicalRecords(MedicalRecords model, List<MedicalRecords> editList) {
        boolean i = false;

        if (editList != null) {
            for (MedicalRecords medicalRecords : editList) {
                if (model.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                        model.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                    editList.remove(medicalRecords);
                    //logger.info("MedicalRecords removed");
                    i = true;
                    break;
                }
            }
            if (i == false) {
                //logger.info("MedicalRecords couldn't be removed&");
            }
        }
    }
}
