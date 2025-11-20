package edu.RL.repository.Repository;

import edu.RL.dto.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BooksRepository {
    ResultSet getAll() throws SQLException;

    void addBook(Book newBook) throws SQLException;

    ResultSet getNextId() throws SQLException;

    void updateBook(Book updateBook) throws SQLException;

    ResultSet searchBook(String bookId, String title) throws SQLException;

    void deleteBook(String bookId) throws SQLException;

    boolean isAvailable(String bookId) throws SQLException;

    void reduceAvailableCopies(String bookId) throws SQLException;

    void increaseAvailableCopies(String bookId) throws SQLException;

    int getBookCount() throws SQLException;
}
