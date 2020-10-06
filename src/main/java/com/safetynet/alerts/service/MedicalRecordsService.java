package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface MedicalRecordsService {

    List<String> getMedicationsFromPerson(Person person);

    List<String> getAllergiesFromPerson(Person person);

    boolean deleteMedicalRecords(MedicalRecords model);

    MedicalRecords updateMedicalRecords(MedicalRecords model);

    MedicalRecords saveMedicalRecords(MedicalRecords model);
}
