package edu.RL.repository;

import edu.RL.db.DBConnection;
import edu.RL.dto.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksRepositoryImpl implements BooksRepository{

    private Connection connection;

    {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ResultSet getAll() throws SQLException {
            String SQL = "SELECT * FROM book";

            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            return resultSet;
    }

    @Override
    public void addBook(Book newBook) throws SQLException {

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
        return connection.prepareStatement("SELECT book_id FROM book ORDER BY book_id DESC LIMIT 1").executeQuery();
    }

    @Override
    public void updateBook(Book updateBook) throws SQLException {

        PreparedStatement psTm = connection.prepareStatement("UPDATE book SET title = ? ,author = ? ,category = ? ,ISBN = ? ,availableNoOfCopies = ? WHERE book_id =?");
        psTm.setObject(1,updateBook.getTitle());
        psTm.setObject(2,updateBook.getAuthor());
        psTm.setObject(3,updateBook.getCategory());
        psTm.setObject(4,updateBook.getIsbn());
        psTm.setObject(5,updateBook.getAvailableCopies());
        psTm.setObject(6,updateBook.getBookId());

        psTm.executeUpdate();
    }

    @Override
    public ResultSet searchBook(String bookId, String title) throws SQLException {
        String SQL = "SELECT * FROM book WHERE book_id = ? OR title= ?";

        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,bookId);
        psTm.setObject(2,title);
        ResultSet resultSet = psTm.executeQuery();
        return resultSet;
    }

    @Override
    public void deleteBook(String bookId) throws SQLException {

        PreparedStatement psTm = connection.prepareStatement("DELETE FROM book WHERE book_id =?");
        psTm.setObject(1,bookId);

        psTm.executeUpdate();
    }

    @Override
    public boolean isAvailable(String bookId) throws SQLException {

        PreparedStatement psTm = connection.prepareStatement("SELECT availableNoOfCopies FROM book WHERE book_id =?");
        psTm.setObject(1,bookId);
        ResultSet resultSet = psTm.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt("availableNoOfCopies")>0;
        }
        return false;
    }

    @Override
    public void reduceAvailableCopies(String bookId) throws SQLException {
        PreparedStatement psTm = connection.prepareStatement("UPDATE book SET availableNoOfCopies=availableNoOfCopies-1 WHERE book_id =? AND availableNoOfCopies>0");
        psTm.setObject(1, bookId);
        psTm.executeUpdate();
    }

    @Override
    public void increaseAvailableCopies(String bookId) throws SQLException {
        PreparedStatement psTm = connection.prepareStatement("UPDATE book SET availableNoOfCopies=availableNoOfCopies+1 WHERE book_id =?");
        psTm.setObject(1, bookId);
        psTm.executeUpdate();
    }
}
