package edu.RL.repository;

import edu.RL.dto.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BooksRepository {
    ResultSet getAll() throws SQLException;

    void addBook(Book newBook) throws SQLException;

    ResultSet getNextId() throws SQLException;
}
