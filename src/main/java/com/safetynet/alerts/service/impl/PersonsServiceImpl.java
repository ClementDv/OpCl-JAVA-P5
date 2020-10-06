package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.exception.ControllerAdvisor;
import com.safetynet.alerts.exception.person.NoChildFoundFromAddressException;
import com.safetynet.alerts.exception.person.NoPersonFoundException;
import com.safetynet.alerts.exception.person.NoPersonFoundFromAddressException;
import com.safetynet.alerts.exception.person.NoPersonFoundFromNameException;
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

import java.util.ArrayList;
import java.util.List;

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
        if (CollectionUtils.isNotEmpty(fullInfoPersonList)) {
            log.info("Request get full information successful!");
            return fullInfoPersonList;
        }
        log.info("Request get full information successful!");
        throw new NoPersonFoundFromNameException(lastName);
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
