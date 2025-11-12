package edu.RL.repository;

import edu.RL.db.DBConnection;
import edu.RL.dto.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksRepositoryImpl implements BooksRepository{

    @Override
    public ResultSet getAll() throws SQLException {
            String SQL = "SELECT * FROM book";

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            return resultSet;
    }

    @Override
    public void addBook(Book newBook) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("INSERT INTO book Values(? ,? ,? ,? ,? ,?)");
        psTm.setObject(1,newBook.getBookId());
        psTm.setObject(2,newBook.getTitle());
        psTm.setObject(3,newBook.getAuthor());
        psTm.setObject(4,newBook.getCategory());
        psTm.setObject(5,newBook.getIsbn());
        psTm.setObject(6,newBook.getAvailableCopies());

        psTm.executeUpdate();
    }

    @Override
    public ResultSet getNextId() throws SQLException {
        return DBConnection.getInstance().getConnection().prepareStatement("SELECT book_id FROM book ORDER BY book_id DESC LIMIT 1").executeQuery();
    }
}
