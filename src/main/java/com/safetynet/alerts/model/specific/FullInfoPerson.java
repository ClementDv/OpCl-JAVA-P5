package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.List;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
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
