package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;

import com.safetynet.alerts.controller.util.View;
import com.safetynet.alerts.model.Person;

import java.util.List;

@JsonView(View.FirestationById.class)
public class FirestationsZone {

    private List<Person> persons;
    private long adults;
    private long children;

    public FirestationsZone(List<Person> persons, long adults, long children) {
        this.persons = persons;
        this.adults = adults;
        this.children = children;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public long getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public long getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "FirestationsZone{" +
                "persons=" + persons +
                ", adults=" + adults +
                ", children=" + children +
                '}';
    }
}
