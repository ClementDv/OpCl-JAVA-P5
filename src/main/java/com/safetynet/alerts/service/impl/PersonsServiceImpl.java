package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.ChildAlert;
import com.safetynet.alerts.model.specific.FullInfoPerson;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.MedicalRecordsService;
import com.safetynet.alerts.service.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonsServiceImpl implements PersonsService {

    @Autowired
    private DataFileAccess dataFileAccess;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @Override
    public ChildAlert getChildAlertFromAddress(String address) {
        ArrayList<FullInfoPerson> listChild = new ArrayList<>();
        ArrayList<FullInfoPerson> listAdult = new ArrayList<>();

        for (Person person : dataFileAccess.getPersonsByAddress(address)) {
            FullInfoPerson fullInfoPerson = new FullInfoPerson(person.getFirstName(), person.getLastName(),
                    null, null, null, null, null, null,
                    dataFileAccess.getAgeFromPerson(person), null, null, 0);
            if (fullInfoPerson.getAge() < 19) {
                listChild.add(fullInfoPerson);
            } else {
                listAdult.add(fullInfoPerson);
            }
        }
        return new ChildAlert(address, listChild, listAdult);
    }

    @Override
    public List<FullInfoPerson> getFullInfoByName(String firstName, String lastName) {
        List<FullInfoPerson> fullInfoPersonList = new ArrayList<>();

        for (Person person : dataFileAccess.getPersons()) {
            if (firstName != null) {
                if (firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())) {
                    fullInfoPersonList.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                            person.getAddress(), person.getCity(), person.getZip(), null, person.getEmail(),
                            null, dataFileAccess.getAgeFromPerson(person), medicalRecordsService.getMedicationsFromPerson(person),
                            medicalRecordsService.getAllergiesFromPerson(person), 0));
                }
            } else {
                if (lastName.equals(person.getLastName())) {
                    fullInfoPersonList.add(new FullInfoPerson(person.getFirstName(), person.getLastName(),
                            person.getAddress(), person.getCity(), person.getZip(), null, person.getEmail(),
                            null, dataFileAccess.getAgeFromPerson(person), medicalRecordsService.getMedicationsFromPerson(person),
                            medicalRecordsService.getAllergiesFromPerson(person), 0));
                }
            }
        }
        return fullInfoPersonList;
    }

    @Override
    public List<Person> getEmailsFromCity(String city) {
        List<Person> listPerson = new ArrayList<>();

        for (Person person : dataFileAccess.getPersons()) {
            if (person.getCity().compareTo(city) == 0) {
                listPerson.add(person);
            }
        }
        return listPerson;
    }

    @Override
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
                //logger.info("Person added");
                return model;
            }
        } else {
            editList.add(model);
            //logger.info("Person added");
            return model;
        }
        //logger.info("Person couldn't be added");
        return null;
    }

    @Override
    public Person updatePerson(Person model, List<Person> editList) {
        if (editList != null) {
            for (Person person : editList) {
                if (model.getFirstName().compareTo(person.getFirstName()) == 0 &&
                        model.getLastName().compareTo(person.getLastName()) == 0) {
                    editList.set(editList.indexOf(person), model);
                    break;
                }
            }
            //logger.info("Person updated");
            return model;
        }
        //logger.info("Person couldn't be updated");
        return null;
    }

    @Override
    public void deletePerson(Person model, List<Person> editList) {
        boolean i = false;

        if (editList != null) {
            for (Person person : editList) {
                if (model.getFirstName().compareTo(person.getFirstName()) == 0 &&
                        model.getLastName().compareTo(person.getLastName()) == 0) {
                    editList.remove(person);
                    //logger.info("Person removed");
                    i = true;
                    break;
                }
            }
            if (i == false) {
                //logger.info("Person couldn't be removed");
            }
        }
    }
}
