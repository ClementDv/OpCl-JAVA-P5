package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;
import com.safetynet.alerts.model.Person;

import java.util.ArrayList;

@JsonView(View.FirestationById.class)
public class FirestationsZone {

    private ArrayList<Person> persons;
    private int adults;
    private int children;

    public FirestationsZone(ArrayList<Person> persons, int adults, int children) {
        this.persons = persons;
        this.adults = adults;
        this.children = children;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
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
