package com.home.mvc.controller;

import java.util.List;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.home.mvc.model.Book;
import com.home.mvc.service.BookService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bs;

    @GetMapping
    public String getBook(@RequestParam(required = false) String login,
                          @RequestParam(required = false) String email,
                          Model model, HttpServletRequest request) {
        var session = request.getSession();
        if (login != null && !login.isEmpty()) {
            session.setAttribute("userLogin", login);
        }

        var userLogin = (String) request.getSession().getAttribute("userLogin");

        model.addAttribute("login", userLogin);

        Model email1 = model.addAttribute("email", email);

        List<Book> books;

        books = bs.getAllBookByLogin(userLogin);

        model.addAttribute("books", books);

        return "book";
    }

    @GetMapping("/create")
    public String createBook(Model model){
        model.addAttribute("newBook",new Book());
        return "create";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book){
        bs.add(book);
        return "redirect:/books";
    }

    @GetMapping("/editPage")
    public String editPage(Model model,@RequestParam(required = false) String title){
        var foundBook = bs.findBookByTitle(title);
        model.addAttribute("editBook",foundBook);
        return "edit";
    }

    @PostMapping("/editBook")
    public String editBook(@ModelAttribute Book book){
        bs.edit(book);
        return  "redirect:/books";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = false)String title){
        bs.findBookByTitle(title);
        return "redirect:/books";
    }
}
