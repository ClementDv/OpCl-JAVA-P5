package com.safetynet.alerts.model;

import java.util.ArrayList;

public class DataFile {

    private ArrayList<Person> persons;
    private ArrayList<Firestations> firestations;
    private ArrayList<MedicalRecords> medicalrecords;

    public DataFile(ArrayList<Person> persons, ArrayList<Firestations> firestations, ArrayList<MedicalRecords> medicalrecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalrecords = medicalrecords;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Firestations> getFirestations() {
        return firestations;
    }

    public void setFirestations(ArrayList<Firestations> firestations) {
        this.firestations = firestations;
    }

    public ArrayList<MedicalRecords> getMedicalrecords() {
        return medicalrecords;
    }


    public void setMedicalrecords(ArrayList<MedicalRecords> medicalrecords) {
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
