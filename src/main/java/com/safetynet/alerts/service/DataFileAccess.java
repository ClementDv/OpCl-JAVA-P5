package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFile;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Scope("singleton")
public class DataFileAccess {

    public DataFile dataFile;

    public DataFileAccess(@Autowired ObjectMapper objectMapper) {
        try {
            this.dataFile = objectMapper.readValue(new File("datafile.json"), DataFile.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(dataFile.toString());
    }

    public int getStationByAddressFromPerson(Person person) {
        int stationNumber = 0;

        for (Firestations fireStation : dataFile.getFirestations()) {
            if (person.getAddress().compareTo(fireStation.getAddress()) == 0) {
                stationNumber = fireStation.getStation();
            }
        }
        return stationNumber;
    }

    public ArrayList<Integer> getStationByAddress(String address) {
        ArrayList<Integer> stationNumber = new ArrayList<Integer>();

        for (Firestations fireStation : dataFile.getFirestations()) {
            if (address.compareTo(fireStation.getAddress()) == 0) {
                stationNumber.add(fireStation.getStation());
            }
        }
        return stationNumber;
    }

    public int getAgeFromPerson(Person person) {
        int age = 0;

        for (MedicalRecords medicalRecords : dataFile.getMedicalrecords()) {
            if (person.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                    person.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                age = getAgeFromBirthdate(medicalRecords.getBirthdate());
            }
        }
        return age;
    }

    private int getAgeFromBirthdate(String birthdate) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        formatter = formatter.withLocale(Locale.FRANCE);
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, currentDate).getYears();
    }

    public ArrayList<String> getMedicationsFromPerson(Person person) {
        for (MedicalRecords medicalRecords : dataFile.getMedicalrecords()) {
            if (person.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                    person.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                return medicalRecords.getMedications();
            }
        }
        return null;
    }

    public ArrayList<String> getAllergiesFromPerson(Person person) {
        for (MedicalRecords medicalRecords : dataFile.getMedicalrecords()) {
            if (person.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                    person.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                return medicalRecords.getAllergies();
            }
        }
        return null;
    }

    public Person savePerson(Person model, List<Person> editList) {
        boolean i = true;
        if (editList != null) {
            for (Person person : editList) {
                if (person.equals(model)) {
                    i = false;
                    break;
                }
            }
            if (i == true) {
                editList.add(model);
                return model;
            }
        } else {
            editList.add(model);
            return model;
        }
        return null;
    }

    public Person updatePerson(Person model, List<Person> editList) {
        if (editList != null) {
            for (Person person : editList) {
                if (model.getFirstName().compareTo(person.getFirstName()) == 0 &&
                        model.getLastName().compareTo(person.getLastName()) == 0) {
                    editList.set(editList.indexOf(person), model);
                    break;
                }
            }
            return model;
        }
        return null;
    }


    public void deletePerson(Person model, List<Person> editList) {
        if (editList != null) {
            for (Person person : editList) {
                if (model.getFirstName().compareTo(person.getFirstName()) == 0 &&
                        model.getLastName().compareTo(person.getLastName()) == 0) {
                    editList.remove(person);
                    break;
                }
            }
        }
    }

    public MedicalRecords saveMedicalRecords(MedicalRecords model, List<MedicalRecords> editList) {
        boolean i = true;
        if (editList != null) {
            for (MedicalRecords medicalRecords : editList) {
                if (medicalRecords.equals(model)) {
                    i = false;
                    break;
                }
            }
            if (i == true) {
                editList.add(model);
                return model;
            }
        } else {
            editList.add(model);
            return model;
        }
        return null;
    }

    public MedicalRecords updateMedicalRecords(MedicalRecords model, List<MedicalRecords> editList) {
        if (editList != null) {
            for (MedicalRecords medicalRecords : editList) {
                if (model.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                        model.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                    editList.set(editList.indexOf(medicalRecords), model);
                    break;
                }
            }
            return model;
        }
        return null;
    }


    public void deleteMedicalRecords(MedicalRecords model, List<MedicalRecords> editList) {
        if (editList != null) {
            for (MedicalRecords medicalRecords : editList) {
                if (model.getFirstName().compareTo(medicalRecords.getFirstName()) == 0 &&
                        model.getLastName().compareTo(medicalRecords.getLastName()) == 0) {
                    editList.remove(medicalRecords);
                    break;
                }
            }
        }
    }

    public Firestations saveFirestation(Firestations model, List<Firestations> editList) {
        boolean i = true;
        if (editList != null) {
            for (Firestations firestations : editList) {
                if (firestations.equals(model)) {
                    i = false;
                    break;
                }
            }
            if (i == true) {
                editList.add(model);
                return model;
            }
        } else {
            editList.add(model);
            return model;
        }
        return null;
    }


    public void deleteFirestation(Firestations model, List<Firestations> editList) {
        if (editList != null) {
            for (Firestations firestations : editList) {
                if (firestations.equals(model)) {
                    editList.remove(firestations);
                    break;
                }
            }
        }
    }

}
