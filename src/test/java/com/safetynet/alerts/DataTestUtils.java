package com.safetynet.alerts;

import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;

import java.util.ArrayList;
import java.util.List;


public class DataTestUtils {

    public static List<Person> personListTest = new ArrayList<>();
    static {
        personListTest.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personListTest.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"));
        personListTest.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
        personListTest.add(new Person("Roger", "Boyd", "29 15th St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        personListTest.add(new Person("Felicia", "Boyd", "29 15th St", "Culver", "97451", "841-874-6544", "jaboyd@email.com"));
    }

    public static List<Firestations> firestationsListTest = new ArrayList<>();
    static {
        firestationsListTest.add(new Firestations("1509 Culver St", 3));
        firestationsListTest.add(new Firestations("29 15th St", 2));
    }

    public static List<MedicalRecords> medicalRecordsListTest = new ArrayList<>();
    static {
        medicalRecordsListTest.add(new MedicalRecords("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")));
        medicalRecordsListTest.add(new MedicalRecords("Jacob", "Boyd", "03/06/1989", List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), List.of()));
        medicalRecordsListTest.add(new MedicalRecords("Tenley", "Boyd", "02/18/2012", List.of(), List.of("peanut")));
        medicalRecordsListTest.add(new MedicalRecords("Roger", "Boyd", "09/06/2017", List.of(), List.of()));
        medicalRecordsListTest.add(new MedicalRecords("Felicia", "Boyd", "01/08/1986", List.of("tetracyclaz:650mg"), List.of("xilliathal")));
    }

    public static Person emptyPerson = new Person("", "", "", "", "", "", "");

    public static Person personToAddTest = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com");

    public static Person personToUpdateTest = new Person("John", "Boyd", "1509 Culver St", "Paris", "97451", "841-874-6512", "jaboyd@email.com");

    public static Person personToDeleteTest = new Person("John", "Boyd", "", "", "", "", "");

    public static Firestations emptyFirestation = new Firestations("", 0);

    public static Firestations firestationToAdd = new Firestations("947 E. Rose Dr", 1);

    public static Firestations firestationToDelete = new Firestations("1509 Culver St", 3);

    public static MedicalRecords emptyMedicalRecord = new MedicalRecords("", "", "", List.of(), List.of());

    public static MedicalRecords medicalRecordsToAddTest = new MedicalRecords("Brian", "Stelzer", "12/06/1975", List.of("ibupurin:200mg", "hydrapermazol:400mg"), List.of("nillacilan"));

    public static MedicalRecords medicalRecordsToUpdateTest = new MedicalRecords("John", "Boyd", "03/06/1984", List.of("doliprane:1000mg"), List.of());

    public static MedicalRecords medicalRecordsToDeleteTest = new MedicalRecords("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"));

}
