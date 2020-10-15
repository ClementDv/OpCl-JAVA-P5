package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoByAddress that = (InfoByAddress) o;
        return Objects.equals(address, that.address) &&
                Objects.equals(Persons, that.Persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, Persons);
    }

    @Override
    public String toString() {
        return "InfoByStation{" +
                ", address='" + address + '\'' +
                ", listPerson=" + Persons +
                '}';
    }
}
