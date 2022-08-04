package com.example.project1.utils;

import com.example.project1.model.Person;
import com.example.project1.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PeopleValid implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PeopleValid(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;

        if(peopleService.findByName(person.getPersonName()).isEmpty() == false) {
            errors.rejectValue("personName", "",
                    "This name is already exist, input other name!");
        }
    }
}
