package edu.RL.service;

import edu.RL.dto.Book;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface BooksService {

    ObservableList<Book> getAll();

    void addBook(Book newBook);

    String generateNextBookId() throws SQLException;
}
