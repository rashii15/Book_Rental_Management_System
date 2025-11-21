package edu.RL.service.Service;

import edu.RL.dto.Book;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface BooksService {

    ObservableList<Book> getAll();

    void addBook(Book newBook);

    String generateNextBookId() throws SQLException;

    void updateBook(Book updateBook);

    Book searchBook(String bookId, String title);

    void deleteBook(String bookId);

    int getBookCount();
}
