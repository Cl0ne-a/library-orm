package com.example.libraryorm;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class ShellUI {

    private final BookService bookService;

    @Autowired
    public ShellUI(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = "hi", value = "actions available:")
    String shellMethodsList() {
        return "Available actions are:\n " +
                "adding,\n " +
                "deleting,\n " +
                "updating,\n listng books\n" +
                "" +
                "Also available serching added book by name or id;\n " +
                "In addition there is an option to create comment to book, and list comment by book id";
    }

    @ShellMethod(key = "add", value = "adding book")
    Book addBook(String title, String authorName, String genreName){
        Author author = Author.builder().name(authorName).build();
        Genre genre = Genre.builder().genre(genreName).build();
        Book book = Book.builder().title(title).author(author).genre(genre).build();

        return book;
    }

    @ShellMethod(key = "list", value = "list books")
    List<Book> list() {
        return bookService.findAll();
    }
}
