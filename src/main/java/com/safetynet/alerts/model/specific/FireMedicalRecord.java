package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.ArrayList;

@JsonView(View.FilterFireEndpoints.class)
public class FireMedicalRecord {

    private ArrayList<Integer> stationNumber;
    private ArrayList<FullInfoPerson> person;

    public FireMedicalRecord(ArrayList<Integer> stationNumber, ArrayList<FullInfoPerson> person) {
        this.stationNumber = stationNumber;
        this.person = person;
    }

    public ArrayList<Integer> getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(ArrayList<Integer> stationNumber) {
        this.stationNumber = stationNumber;
    }

    public ArrayList<FullInfoPerson> getPerson() {
        return person;
    }

    public void setPerson(ArrayList<FullInfoPerson> person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "FireMedicalRecord{" +
                "stationNumber=" + stationNumber +
                ", person=" + person +
                '}';
    }
}
