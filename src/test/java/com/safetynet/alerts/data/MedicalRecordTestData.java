package com.safetynet.alerts.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicalRecordTestData {

    public static List<String> getMedicationsFromPersonList() {
        return new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
    }

    public static List<String> getAllergiesFromPersonList() {
        return new ArrayList<>(Arrays.asList("nillacilan"));
    }
}
