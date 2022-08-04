package com.example.project1.controller;

import com.example.project1.model.Book;
import com.example.project1.model.Person;
import com.example.project1.service.BooksService;
import com.example.project1.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String allBooks(Model model, @RequestParam(value = "page", required = false) String page,
                           @RequestParam(value = "books_per_page", required = false) String booksPerPage,
                           @RequestParam(value = "sort_by_year", required = false) boolean sort) {

        if (page != null || booksPerPage != null) {
            model.addAttribute("books", booksService.paginAndSortBooks(page, booksPerPage, sort));
        } else {
            model.addAttribute("books", booksService.allBooks(sort));
        }

        return "books/allBooks";
    }


    @GetMapping("/{id}")
    public String book(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.book(id));
        model.addAttribute("people", peopleService.allPeople());
        model.addAttribute("reader", booksService.whereBook(id));
        return "books/book";
    }


    @GetMapping("/newBook")
    public String bookFormCreate(@ModelAttribute("book") Book book) {
        return "books/newBook";
    }


    @PostMapping()
    public String saveBook(@ModelAttribute("book") Book book) {
        booksService.saveBook(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}/updateBook")
    public String bookFormUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.book(id));
        return "books/updateBook";
    }


    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        booksService.updateBook(id, book);
        return "redirect:/books";
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.deleteBook(id);
        return "redirect:/books";
    }


    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        peopleService.assignBook(person.getId(), id);
        return "redirect:/books";
    }


    @PatchMapping("/{id}/freeBook")
    public String freeBook(@PathVariable("id") int id) {
        booksService.freeBook(id);
        return "redirect:/books";
    }


    @GetMapping("/search")
    public String searchBook(@ModelAttribute("book") Book book,
                             @RequestParam(value = "bookName", required = false) String bookName,
                             Model model) {
        if (bookName != null) {
            model.addAttribute("books", booksService.findBookByStartingLetter(bookName));
        }
        return "books/searchBook";
    }

}
