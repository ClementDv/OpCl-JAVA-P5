package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

    @Autowired
    private DataFileAccess dataFileAccess;

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
            if (i == true) {
                editList.add(model);
                //logger.info("MedicalRecords added");
                return model;
            }
        } else {
            editList.add(model);
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
