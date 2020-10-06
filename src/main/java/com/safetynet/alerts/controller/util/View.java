package com.safetynet.alerts.controller.util;

public class View {
    public interface FirstName {
    }

    public interface LastName {
    }

    public interface Address {
    }

    public interface Phone {
    }

    public interface Email {
    }

    public interface FirestationById extends FirstName, LastName, Address, Phone {
    }

    public interface FullFirstName {
    }

    public interface FullLastName {
    }

    public interface FullAddress {
    }

    public interface FullCity {
    }

    public interface FullZip {
    }

    public interface FullPhone {
    }

    public interface FullEmail {
    }

    public interface FullBirthdate {
    }

    public interface FullAge {
    }

    public interface FullMedications {
    }

    public interface FullAllergies {
    }

    public interface FullStation {
    }

    public interface FilterFireEndpoints extends FullFirstName, FullLastName, FullPhone, FullAge, FullMedications, FullAllergies {
    }

    public interface FilterChildAlertEndpoints extends FullFirstName, FullLastName, FullAge {
    }

    public interface FilterFloodStations extends FullFirstName, FullLastName, FullPhone, FullAge, FullMedications, FullAllergies {
    }

    public interface FilterFullInfoByName extends FullFirstName, FullLastName, FullAddress, FullCity, FullZip, FullEmail, FullAge, FullMedications, FullAllergies {
    }
}
