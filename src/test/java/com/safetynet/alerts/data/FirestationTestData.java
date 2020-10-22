package com.safetynet.alerts.data;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirestationTestData {

    public static FirestationsZone getFirestationsZoneTest() {
        return new FirestationsZone(
                new ArrayList<>(Arrays.asList(
                        new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
                        new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"),
                        new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"))),
                2,
                1);
    }

    public static FirestationsZone getFirestationsZoneControllerTest() {
        return new FirestationsZone(
                new ArrayList<>(Arrays.asList(
                        new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", null),
                        new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", null),
                        new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", null))),
                2,
                1);
    }

    public static List<String> getPhoneAlertStringListTest() {
        return new ArrayList<>(Arrays.asList("841-874-6512", "841-874-6544"));
    }

    public static List<FullInfoPerson> getFullInfoPersonListFireStationNumberThreeTest() {
        return new ArrayList<>(Arrays.asList(
                new FullInfoPerson("John", "Boyd", null, null, null, "841-874-6512", null, null, 36, List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"), 0),
                new FullInfoPerson("Jacob", "Boyd", null, null, null, "841-874-6513", null, null, 31, List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), List.of(), 0),
                new FullInfoPerson("Tenley", "Boyd", null, null, null, "841-874-6512", null, null, 8, List.of(), List.of("peanut"), 0)
        ));
    }

    public static List<FullInfoPerson> getFullInfoPersonListFireStationNumberTwoTest() {
        return new ArrayList<>(Arrays.asList(
                new FullInfoPerson("Roger", "Boyd", null, null, null, "841-874-6512", null, null, 3, List.of(), List.of(), 0),
                new FullInfoPerson("Felicia", "Boyd", null, null, null, "841-874-6544", null, null, 34, List.of("tetracyclaz:650mg"), List.of("xilliathal"), 0)
        ));
    }

    public static FireMedicalRecord getFireMedicalRecordTest() {
        return new FireMedicalRecord(List.of(3), getFullInfoPersonListFireStationNumberThreeTest());
    }

    public static List<InfoByStation> getInfoByStationsListTest() {
        return new ArrayList<>(List.of(
                new InfoByStation(List.of(new InfoByAddress("1509 Culver St", getFullInfoPersonListFireStationNumberThreeTest())), 3),
                new InfoByStation(List.of(new InfoByAddress("29 15th St", getFullInfoPersonListFireStationNumberTwoTest())), 2)
        ));
    }
}
