package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonsServiceImpl implements PersonsService {

    @Autowired
    private DataFileAccess dataFileAccess;

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
