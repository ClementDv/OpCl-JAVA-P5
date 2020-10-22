package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.List;
import java.util.Objects;

public class FullInfoPerson {

    @JsonView(View.FullFirstName.class)
    private String firstName;

    @JsonView(View.FullLastName.class)
    private String lastName;

    @JsonView(View.FullAddress.class)
    private String address;

    @JsonView(View.FullCity.class)
    private String city;

    @JsonView(View.FullZip.class)
    private String zip;

    @JsonView(View.FullPhone.class)
    private String phone;

    @JsonView(View.FullEmail.class)
    private String email;

    @JsonView(View.FullBirthdate.class)
    private String Birthdate;

    @JsonView(View.FullAge.class)
    private int age;

    @JsonView(View.FullMedications.class)
    private List<String> medications;

    @JsonView(View.FullAllergies.class)
    private List<String> allergies;

    @JsonView(View.FullStation.class)
    private int station;

    public FullInfoPerson(String firstName, String lastName, String address, String city, String zip, String phone, String email, String birthdate, int age, List<String> medications, List<String> allergies, int station) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        Birthdate = birthdate;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
        this.station = station;
    }

    public FullInfoPerson() {
    }

    public String getFirstName() {
        return firstName;
    }

    public FullInfoPerson setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public FullInfoPerson setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public FullInfoPerson setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public FullInfoPerson setCity(String city) {
        this.city = city;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public FullInfoPerson setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public FullInfoPerson setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public FullInfoPerson setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public FullInfoPerson setBirthdate(String birthdate) {
        Birthdate = birthdate;
        return this;
    }

    public int getAge() {
        return age;
    }

    public FullInfoPerson setAge(int age) {
        this.age = age;
        return this;
    }

    public List<String> getMedications() {
        return medications;
    }

    public FullInfoPerson setMedications(List<String> medications) {
        this.medications = medications;
        return this;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public FullInfoPerson setAllergies(List<String> allergies) {
        this.allergies = allergies;
        return this;
    }

    public int getStation() {
        return station;
    }

    public FullInfoPerson setStation(int station) {
        this.station = station;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullInfoPerson that = (FullInfoPerson) o;
        return age == that.age &&
                station == that.station &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(Birthdate, that.Birthdate) &&
                Objects.equals(medications, that.medications) &&
                Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, city, zip, phone, email, Birthdate, age, medications, allergies, station);
    }

    @Override
    public String toString() {
        return "FullInfoPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", Birthdate='" + Birthdate + '\'' +
                ", age=" + age +
                ", medications=" + medications +
                ", allergies=" + allergies +
                ", station=" + station +
                '}';
    }
}
