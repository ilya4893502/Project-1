package com.example.project1.service;

import com.example.project1.model.Book;
import com.example.project1.model.Person;
import com.example.project1.repository.BooksRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final EntityManager entityManager;

    @Autowired
    public BooksService(BooksRepository booksRepository, EntityManager entityManager) {
        this.booksRepository = booksRepository;
        this.entityManager = entityManager;
    }


    public List<Book> allBooks(boolean sort) {

        if (sort) {
            return booksRepository.findAll(Sort.by("year"));
        } else {
            return booksRepository.findAll();
        }
    }


    public Book book(int id) {
        return booksRepository.findById(id).orElse(null);
    }


    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
    }


    @Transactional
    public void updateBook(int id, Book book) {
        book.setBookId(id);
        booksRepository.save(book);
    }


    @Transactional
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }


    @Transactional
    public void freeBook(int id) {
        Session session = entityManager.unwrap(Session.class);

        Book book = session.get(Book.class, id);
        Person person = book.getReader();

        book.setTakeTime(null);

        person.getBooks().remove(book);
        book.setReader(null);
    }


    public Person whereBook(int id) {
        Session session = entityManager.unwrap(Session.class);

        Book book = session.get(Book.class, id);
        Person person = book.getReader();

        return person;
    }


    public List<Book> findBookByStartingLetter(String startLetters) {
        List<Book> book = booksRepository.findByBookNameStartingWith(startLetters);
        return book;
    }


    public Page<Book> paginAndSortBooks(String page, String booksPerPage, boolean sort) {

        Page<Book> pages;

        if(sort) {
            pages = booksRepository.findAll(PageRequest.of(Integer.parseInt(page),
                    Integer.parseInt(booksPerPage), Sort.by("year")));
        } else {
            pages =  booksRepository.findAll(PageRequest.of(Integer.parseInt(page),
                    Integer.parseInt(booksPerPage)));
        }

        return pages;
    }
}
