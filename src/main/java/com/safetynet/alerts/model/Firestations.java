package com.safetynet.alerts.model;

import java.util.Objects;

public class Firestations {

    private String address;
    private int station;

    public Firestations(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public Firestations() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Firestations{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Firestations that = (Firestations) o;
        return station == that.station &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }
}
