package com.example.project1.controller;

import com.example.project1.model.Person;
import com.example.project1.service.PeopleService;
import com.example.project1.utils.PeopleValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PeopleValid peopleValid;

    @Autowired
    public PeopleController(PeopleService peopleService, PeopleValid peopleValid) {
        this.peopleService = peopleService;
        this.peopleValid = peopleValid;
    }

    @GetMapping()
    public String allPeople(Model model) {
        model.addAttribute("people", peopleService.allPeople());
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String person(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.person(id));
        model.addAttribute("books", peopleService.bookReader(id));
        model.addAttribute("isOverdue", peopleService.overdue(id));
        return "people/person";
    }

    @GetMapping("/newPerson")
    public String personFormCreate(@ModelAttribute("person") Person person) {
        return "people/newPerson";
    }

    @PostMapping()
    public String savePerson(@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult) {
        peopleValid.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            return "people/newPerson";
        }
        peopleService.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/updatePerson")
    public String personFormUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.person(id));
        return "people/updatePerson";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        peopleValid.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/updatePerson";
        }
        peopleService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.deletePerson(id);
        return "redirect:/people";
    }
}
