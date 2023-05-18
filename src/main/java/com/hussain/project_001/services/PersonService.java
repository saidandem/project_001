package com.hussain.project_001.services;

import com.hussain.project_001.model.Person;

import java.util.List;

public class PersonService {
    public List<Person> getPersons() {
        return MockPersonsDB.persons;
    }
}
