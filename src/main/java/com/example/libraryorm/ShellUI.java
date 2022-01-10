package com.example.libraryorm;

import com.example.libraryorm.entities.Author;
import com.example.libraryorm.entities.Book;
import com.example.libraryorm.entities.Comment;
import com.example.libraryorm.entities.Genre;
import com.example.libraryorm.service.AuthorService;
import com.example.libraryorm.service.BookService;
import com.example.libraryorm.service.CommentService;
import com.example.libraryorm.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class ShellUI {

    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public ShellUI(CommentService commentService,
                   GenreService genreService,
                   AuthorService authorService,
                   BookService bookService) {
        this.commentService = commentService;
        this.genreService = genreService;
        this.authorService = authorService;
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

    // genre service
    @ShellMethod(key = "genre-save", value = "adding gere")
    Genre addGenre(String genreName) {
        Genre genre = Genre.builder().genre(genreName).build();
        return genreService.addGenre(genre);
    }

    @ShellMethod(key = "genres", value = "list genres")
    List<Genre> listGenres() {
        return genreService.listGenres();
    }


    // author service
    @ShellMethod(key = "author-save", value = "adding author")
    Author addAuhtor(String name) {
        Author author = Author.builder().name(name).build();
        return authorService.addAuthor(author);
    }

    @ShellMethod(key = "authors", value = "list authors")
    List<Author> listAuthors() {
        return authorService.listAuthors();
    }


    // book service
    @ShellMethod(key = "book-save", value = "adding book")
    Book addBook(String title, String authorName, String genreName){
        Author author = Author.builder().name(authorName).build();
        Genre genre = Genre.builder().genre(genreName).build();
        Book book = Book.builder().title(title).author(author).genre(genre).build();

        return bookService.addBook(book);
    }

     @ShellMethod(key = "delete", value = "delete book by id")
     Book deleteById(int id) {

         Book book = bookService.findById(id);
         bookService.deleteById(id);
         return book;
     }

    @ShellMethod(key = "id", value = "find book by id")
    Book findById(int id) {
        return bookService.findById(id);
    }

    @ShellMethod(key = "title", value = "find book by title")
    Book findByTitle(String title) {
        return bookService.findByTitle(title);
    }

    @ShellMethod(key = "update", value = "update book title by id")
    Book updateBook(int id, String title) {
        bookService.updateTitleById(id, title);
        return bookService.findById(id);
    }

    @ShellMethod(key = "books", value = "list books")
    List<Book> listBooks() {
        return bookService.findAll();
    }

    // comment service
    @ShellMethod(key = "feedback", value = "comment for the book byid")
    Comment writeComment(String comment, int bookId) {
        return commentService.addComment(comment, bookId);
    }

    @ShellMethod(key = "comments", value = "displays list of comments by certain book id")
    List<Comment> findCommentsByBook(int bookId) {
        return commentService.findByBookId(bookId);
    }

    @ShellMethod(key = "comment-list", value = "list all the comments")
    List<Comment> listComments() {
        return commentService.listAll();
    }
}
