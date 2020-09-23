package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.ArrayList;
import java.util.List;

@JsonView(View.FilterFloodStations.class)
public class InfoByAddress {
    private String address;
    private List<FullInfoPerson> Persons;

    public InfoByAddress(String address, List<FullInfoPerson> listPerson) {
        this.address = address;
        this.Persons = listPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FullInfoPerson> getPersons() {
        return Persons;
    }

    public void setPersons(List<FullInfoPerson> persons) {
        this.Persons = persons;
    }

    public void addPerson(FullInfoPerson person) {
        this.Persons.add(person);
    }

    @Override
    public String toString() {
        return "InfoByStation{" +
                ", address='" + address + '\'' +
                ", listPerson=" + Persons +
                '}';
    }
}
