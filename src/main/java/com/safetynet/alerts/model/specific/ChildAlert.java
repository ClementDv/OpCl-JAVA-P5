package com.safetynet.alerts.model.specific;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.alerts.controller.util.View;

import java.util.List;
import java.util.Objects;


@JsonView(View.FilterChildAlertEndpoints.class)
public class ChildAlert {

    private String address;
    private List<FullInfoPerson> Child;
    private List<FullInfoPerson> Adults;

    public ChildAlert(String address, List<FullInfoPerson> child, List<FullInfoPerson> adults) {
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

    public List<FullInfoPerson> getChild() {
        return Child;
    }

    public void setChild(List<FullInfoPerson> child) {
        Child = child;
    }

    public List<FullInfoPerson> getAdults() {
        return Adults;
    }

    public void setAdults(List<FullInfoPerson> adults) {
        Adults = adults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildAlert that = (ChildAlert) o;
        return Objects.equals(address, that.address) &&
                Objects.equals(Child, that.Child) &&
                Objects.equals(Adults, that.Adults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, Child, Adults);
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
