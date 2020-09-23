package com.safetynet.alerts.model;

import java.util.ArrayList;
import java.util.List;

public class DataFile {

    private List<Person> persons;
    private List<Firestations> firestations;
    private List<MedicalRecords> medicalrecords;

    public DataFile(List<Person> persons, List<Firestations> firestations, List<MedicalRecords> medicalrecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalrecords = medicalrecords;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Firestations> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestations> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecords> getMedicalrecords() {
        return medicalrecords;
    }


    public void setMedicalrecords(List<MedicalRecords> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }


    @Override
    public String toString() {
        return "DataFile{" +
                "persons=" + persons +
                ", firestations=" + firestations +
                ", medicalrecords=" + medicalrecords +
                '}';
    }
}
