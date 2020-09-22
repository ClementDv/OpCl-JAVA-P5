package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecords;

import java.util.List;

public interface MedicalRecordsService {

    void deleteMedicalRecords(MedicalRecords model, List<MedicalRecords> editList);

    MedicalRecords updateMedicalRecords(MedicalRecords model, List<MedicalRecords> editList);

    MedicalRecords saveMedicalRecords(MedicalRecords model, List<MedicalRecords> editList);
}
