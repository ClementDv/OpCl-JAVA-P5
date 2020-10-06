package com.safetynet.alerts.exception.person;

public class NoPersonFoundException extends RuntimeException {

    public NoPersonFoundException() {
        super("No person(s) found!");
    }

}
