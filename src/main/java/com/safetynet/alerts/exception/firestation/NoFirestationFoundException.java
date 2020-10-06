package com.safetynet.alerts.exception.firestation;

import java.util.List;

public class NoFirestationFoundException extends RuntimeException {

    private final List<Integer> firestationNb;

    public NoFirestationFoundException(List<Integer> firestationNb){
        super("No Firestation(s) found for number : " + firestationNb + " !");
        this.firestationNb = firestationNb;
    }

    public List<Integer> getFirestationNb() {
        return firestationNb;
    }
}
