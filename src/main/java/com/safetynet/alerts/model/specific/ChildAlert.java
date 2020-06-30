package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.ArrayList;


@JsonView(View.FilterChildAlertEndpoints.class)
public class ChildAlert {

    private String address;
    private ArrayList<FullInfoPerson> Child;
    private ArrayList<FullInfoPerson> Adults;

    public ChildAlert(String address, ArrayList<FullInfoPerson> child, ArrayList<FullInfoPerson> adults) {
        this.address = address;
        Child = child;
        Adults = adults;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<FullInfoPerson> getChild() {
        return Child;
    }

    public void setChild(ArrayList<FullInfoPerson> child) {
        Child = child;
    }

    public ArrayList<FullInfoPerson> getAdults() {
        return Adults;
    }

    public void setAdults(ArrayList<FullInfoPerson> adults) {
        Adults = adults;
    }

    @Override
    public String toString() {
        return "ChildAlert{" +
                "address='" + address + '\'' +
                ", Child=" + Child +
                ", Adults=" + Adults +
                '}';
    }
}
