package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;

import com.safetynet.alerts.controller.util.View;
import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirestationsZone that = (FirestationsZone) o;
        return adults == that.adults &&
                children == that.children &&
                Objects.equals(persons, that.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, adults, children);
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
