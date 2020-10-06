package com.safetynet.alerts.exception.person;

public class NoChildFoundFromAddressException extends RuntimeException {

    private final String address;

    public NoChildFoundFromAddressException(String address) {
        super("No child(ren) found for address : " + address + " !");
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}