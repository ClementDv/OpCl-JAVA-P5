package com.safetynet.alerts.exception.person;

public class NoPersonFoundFromAddressException extends RuntimeException {

    private final String address;

    public NoPersonFoundFromAddressException(String address) {
        super("No person(s) found for address : " + address + " !");
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
