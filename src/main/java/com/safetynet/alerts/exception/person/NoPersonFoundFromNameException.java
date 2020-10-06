package com.safetynet.alerts.exception.person;

public class NoPersonFoundFromNameException extends RuntimeException {

    private final String name;

    public NoPersonFoundFromNameException(String name) {
        super("No person(s) named : " + name + " found!");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
