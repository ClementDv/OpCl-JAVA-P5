package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.List;
import java.util.Objects;

@JsonView(View.FilterFireEndpoints.class)
public class FireMedicalRecord {

    private List<Integer> stationNumber;
    private List<FullInfoPerson> person;

    public FireMedicalRecord(List<Integer> stationNumber, List<FullInfoPerson> person) {
        this.stationNumber = stationNumber;
        this.person = person;
    }

    public List<Integer> getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(List<Integer> stationNumber) {
        this.stationNumber = stationNumber;
    }

    public List<FullInfoPerson> getPerson() {
        return person;
    }

    public void setPerson(List<FullInfoPerson> person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireMedicalRecord that = (FireMedicalRecord) o;
        return Objects.equals(stationNumber, that.stationNumber) &&
                Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationNumber, person);
    }

    @Override
    public String toString() {
        return "FireMedicalRecord{" +
                "stationNumber=" + stationNumber +
                ", person=" + person +
                '}';
    }
}
