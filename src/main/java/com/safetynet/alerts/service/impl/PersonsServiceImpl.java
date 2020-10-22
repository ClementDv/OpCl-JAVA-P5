package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.exception.ControllerAdvisor;
import com.safetynet.alerts.exception.person.*;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.specific.ChildAlert;
import com.safetynet.alerts.model.specific.FullInfoPerson;
import com.safetynet.alerts.repository.DataFileAccess;
import com.safetynet.alerts.service.MedicalRecordsService;
import com.safetynet.alerts.service.PersonsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonsServiceImpl implements PersonsService {

    private static final Logger log = LogManager.getLogger(ControllerAdvisor.class);

    @Autowired
    private DataFileAccess dataFileAccess;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @Override
    public ChildAlert getChildAlertFromAddress(String address) {
        List<FullInfoPerson> listChild = new ArrayList<>();
        List<FullInfoPerson> listAdult = new ArrayList<>();

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
        if (CollectionUtils.isNotEmpty(listChild)) {
            log.info("Request get child alerts successful!");
            return new ChildAlert(address, listChild, listAdult);
        }
        log.info("Request get child alerts failed.");
        throw new NoChildFoundFromAddressException(address);
    }

    @Override
    public List<FullInfoPerson> getFullInfoByName(String firstName, String lastName) {
        List<Person> allPersons = dataFileAccess.getPersons();
        List<FullInfoPerson> fullInfoPersonList = allPersons.stream()
            .filter(person -> {
                boolean lastNameEquals = person.getLastName().equalsIgnoreCase(lastName);
                boolean firstNameEquals = !StringUtils.isEmpty(firstName) ? person.getFirstName().equalsIgnoreCase(firstName) : true;
                return lastNameEquals && firstNameEquals;
            })
            .map(p -> new FullInfoPerson()
                .setFirstName(p.getFirstName())
                .setLastName(p.getLastName())
                .setAddress(p.getAddress())
                .setCity(p.getCity())
                .setZip(p.getZip())
                .setEmail(p.getEmail())
                .setStation(0)
                .setAge(dataFileAccess.getAgeFromPerson(p))
                .setMedications(medicalRecordsService.getMedicationsFromPerson(p))
                .setAllergies(medicalRecordsService.getAllergiesFromPerson(p))
            )
            .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(fullInfoPersonList)) {
            log.info("Request get full information successful!");
            return fullInfoPersonList;
        }
        log.info("Request get full information successful!");
        if (firstName == null || firstName.isEmpty())
            throw new NoPersonFoundFromNameException(lastName);
        throw new NoPersonFoundFromFirstNameAndNameException(firstName, lastName);
    }

    @Override
    public List<String> getEmailsFromCity(String city) {
        List<String> emailList = new ArrayList<>();

        for (Person person : dataFileAccess.getPersons()) {
            if (person.getCity().compareTo(city) == 0) {
                emailList.add(person.getEmail());
            }
        }
        if (CollectionUtils.isNotEmpty(emailList)) {
            log.info("Request get email successful!");
            return emailList;
        }
        log.info("Request get email failed.");
        throw new NoPersonFoundException();
    }

    @Override
    public Person savePerson(Person model) {
        Person result = dataFileAccess.savePerson(model);
        if (result != null) log.info("Request save person successful");
        log.info("Request save person failed");
        return result;
    }

    @Override
    public Person updatePerson(Person model) {
        Person result = dataFileAccess.updatePerson(model);
        if (result != null) log.info("Request update person sucessful");
        log.info("Request update person failed");
        return result;
    }

    @Override
    public boolean deletePerson(Person model) {
        boolean result = dataFileAccess.deletePerson(model);
        if (result) log.info("Request delete person successful.");
        log.info("Request delete person failed.");
        return result;
    }
}
