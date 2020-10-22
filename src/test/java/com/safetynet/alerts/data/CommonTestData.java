package com.safetynet.alerts.data;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommonTestData {
    public static List<Person> getPersonList() {
        return new ArrayList<>(Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"),
                new Person("Roger", "Boyd", "29 15th St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
                new Person("Felicia", "Boyd", "29 15th St", "Culver", "97451", "841-874-6544", "jaboyd@email.com")
        ));
    }

    public static List<Firestations> getFirestationsList() {
        return new ArrayList<>(Arrays.asList(
                new Firestations("1509 Culver St", 3),
                new Firestations("29 15th St", 2)
        ));
    }

    public static List<MedicalRecords> getMedicalRecordsList() {
        return new ArrayList<>(Arrays.asList(
                new MedicalRecords("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new MedicalRecords("Jacob", "Boyd", "03/06/1989", List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), List.of()),
                new MedicalRecords("Tenley", "Boyd", "02/18/2012", List.of(), List.of("peanut")),
                new MedicalRecords("Roger", "Boyd", "09/06/2017", List.of(), List.of()),
                new MedicalRecords("Felicia", "Boyd", "01/08/1986", List.of("tetracyclaz:650mg"), List.of("xilliathal"))
        ));
    }

    public static Person getEmptyPerson() {
        return new Person("", "", "", "", "", "", "");
    }

    public static Person getPersonToAddTest() {
        return new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com");
    }

    public static Person getPersonToUpdateTest() {
        return new Person("John", "Boyd", "1509 Culver St", "Paris", "97451", "841-874-6512", "jaboyd@email.com");
    }

    public static Person getPersonToDeleteTest() {
        return new Person("John", "Boyd", "", "", "", "", "");
    }

    public static Firestations getEmptyFirestation() {
        return new Firestations("", 0);
    }

    public static Firestations getFirestationToAdd() {
        return new Firestations("947 E. Rose Dr", 1);
    }

    public static Firestations getFirestationToDelete() {
        return new Firestations("1509 Culver St", 3);
    }

    public static MedicalRecords getEmptyMedicalRecord() {
        return new MedicalRecords("", "", "", List.of(), List.of());
    }

    public static MedicalRecords getMedicalRecordsToAddTest() {
        return new MedicalRecords("Brian", "Stelzer", "12/06/1975", List.of("ibupurin:200mg", "hydrapermazol:400mg"), List.of("nillacilan"));
    }

    public static MedicalRecords getMedicalRecordsToUpdateTest() {
        return new MedicalRecords("John", "Boyd", "03/06/1984", List.of("doliprane:1000mg"), List.of());
    }

    public static MedicalRecords getMedicalRecordsToDeleteTest() {
        return new MedicalRecords("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"));
    }
}
