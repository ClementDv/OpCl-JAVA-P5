package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface MedicalRecordsService {

    List<String> getMedicationsFromPerson(Person person);

    List<String> getAllergiesFromPerson(Person person);

    void deleteMedicalRecords(MedicalRecords model, List<MedicalRecords> editList);

    MedicalRecords updateMedicalRecords(MedicalRecords model, List<MedicalRecords> editList);

    MedicalRecords saveMedicalRecords(MedicalRecords model, List<MedicalRecords> editList);
}
