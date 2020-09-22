package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;

import java.util.List;

public interface PersonsService {

    Person savePerson(Person model, List<Person> editList);

    Person updatePerson(Person model, List<Person> editList);

    void deletePerson(Person model, List<Person> editList);

}
