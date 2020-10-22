package com.safetynet.alerts.data;

import com.safetynet.alerts.model.specific.ChildAlert;
import com.safetynet.alerts.model.specific.FullInfoPerson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonTestData {

    public static List<FullInfoPerson> getFullInfoPersonListChild() {
        return new ArrayList<>(Arrays.asList(
                new FullInfoPerson("Tenley", "Boyd", null, null, null, null, null, null, 8, null, null, 0)
        ));
    }

    public static List<FullInfoPerson> getFullInfoPersonListAdult() {
        return new ArrayList<>(Arrays.asList(
                new FullInfoPerson("John", "Boyd", null, null, null, null, null, null, 36, null, null, 0),
                new FullInfoPerson("Jacob", "Boyd", null, null, null, null, null, null, 31, null, null, 0)
        ));
    }

    public static ChildAlert getChildAlertTest() {
        return new ChildAlert("1509 Culver St", getFullInfoPersonListChild(), getFullInfoPersonListAdult());
    }

    public static List<FullInfoPerson> getFullInfoPersonByNameList() {
        return new ArrayList<>(Arrays.asList(
                new FullInfoPerson("John", "Boyd", "1509 Culver St", "Culver", "97451", null, "jaboyd@email.com", null, 36, List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"), 0),
                new FullInfoPerson("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", null, "drk@email.com", null, 31, List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), List.of(), 0),
                new FullInfoPerson("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", null, "tenz@email.com", null, 8, List.of(), List.of("peanut"), 0),
                new FullInfoPerson("Roger", "Boyd", "29 15th St", "Culver", "97451", null, "jaboyd@email.com", null, 3, List.of(), List.of(), 0),
                new FullInfoPerson("Felicia", "Boyd", "29 15th St", "Culver", "97451", null, "jaboyd@email.com", null, 34, List.of("tetracyclaz:650mg"), List.of("xilliathal"), 0)
        ));
    }

    public static List<String> getEmailsFromCityList() {
        return new ArrayList<>(Arrays.asList("jaboyd@email.com", "drk@email.com", "tenz@email.com", "jaboyd@email.com", "jaboyd@email.com"));
    }
}
