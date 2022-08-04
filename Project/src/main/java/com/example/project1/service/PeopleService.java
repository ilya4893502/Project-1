package com.example.project1.service;

import com.example.project1.model.Book;
import com.example.project1.model.Person;
import com.example.project1.repository.BooksRepository;
import com.example.project1.repository.PeopleRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final EntityManager entityManager;
    private final BooksRepository booksRepository;
    private final BooksService booksService;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager, BooksRepository booksRepository, BooksService booksService) {
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
        this.booksRepository = booksRepository;
        this.booksService = booksService;
    }


    public List<Person> allPeople() {
        return peopleRepository.findAll();
    }


    public Person person(int id) {
        return peopleRepository.findById(id).orElse(null);
    }


    @Transactional
    public void savePerson(Person person) {
        peopleRepository.save(person);
    }


    @Transactional
    public void updatePerson(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }


    @Transactional
    public void deletePerson(int id) {
        peopleRepository.deleteById(id);
    }


    public List<Book> bookReader(int id) {
        Session session = entityManager.unwrap(Session.class);

        Person person = session.get(Person.class, id);
        Hibernate.initialize(person.getBooks());
        List<Book> booksOfPerson = person.getBooks();

        return booksOfPerson;
    }


    @Transactional
    public void assignBook(int personId, int bookId) {
        Session session = entityManager.unwrap(Session.class);

        Person person = session.get(Person.class, personId);
        Book book = session.get(Book.class, bookId);

        book.setTakeTime(new Date());

        person.getBooks().add(book);
        book.setReader(person);
    }


    public List<Person> findByName(String name){
        List<Person> findPeople = peopleRepository.findByPersonName(name);
        return findPeople;
    }


    public boolean[] overdue(int id) {
        List<Book> books = bookReader(id);
        long tenDays = 864_000_000;
        boolean isOverdue = false;

        boolean [] overdue = new boolean[books.size()];

        for (Book book : books) {

            long timeBook = book.getTakeTime().getTime();
            long currentTime = new Date().getTime();

            if (currentTime - timeBook > tenDays) {
                isOverdue = true;
            } else {
                isOverdue = false;
            }

            overdue[books.indexOf(book)] = isOverdue;
        }

        return overdue;
    }

}
