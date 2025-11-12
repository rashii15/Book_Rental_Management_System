package edu.RL.service;

import edu.RL.dto.Book;
import edu.RL.repository.BooksRepository;
import edu.RL.repository.BooksRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BooksServiceImpl implements BooksService{
    BooksRepository booksRepository = new BooksRepositoryImpl();

    @Override
    public ObservableList<Book> getAll(){
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = booksRepository.getAll();
            while (resultSet.next()) {
                bookObservableList.add(new Book(
                        resultSet.getString("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("category"),
                        resultSet.getString("ISBN"),
                        resultSet.getInt("availableNoOfCopies"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookObservableList;

    }

    @Override
    public void addBook(Book newBook) {
        try {
            booksRepository.addBook(newBook);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateNextBookId() throws SQLException {
        ResultSet resultSet = booksRepository.getNextId();
        try {
            if (resultSet.next()) {
                String lastId = resultSet.getString("book_id");
                int idNum = Integer.parseInt(lastId.substring(1));
                idNum++;
                return String.format("B%03d", idNum);
            } else {
                return "B001";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to generate book Id",e);
        }
    }
}