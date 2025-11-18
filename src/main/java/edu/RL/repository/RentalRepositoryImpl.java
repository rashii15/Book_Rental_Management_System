package edu.RL.repository;

import edu.RL.db.DBConnection;
import edu.RL.dto.Rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 public class RentalRepositoryImpl implements RentalRepository{
    @Override
    public ResultSet getAll() throws SQLException {
        return DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM rental").executeQuery();
    }

    @Override
    public boolean addRental(Rental newRental) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("INSERT INTO rental Values(? ,? ,? ,? ,? ,? ,0)");
        psTm.setObject(1,newRental.getRentalId());
        psTm.setObject(2,newRental.getCustomerId());
        psTm.setObject(3,newRental.getBookId());
        psTm.setObject(4,newRental.getIssueDate());
        psTm.setObject(5,newRental.getDueDate());
        psTm.setObject(6,newRental.getReturnDate());

        return psTm.executeUpdate() >0;
    }

    @Override
    public ResultSet getNextId() throws SQLException {
        return DBConnection.getInstance().getConnection().prepareStatement("SELECT rental_id FROM rental ORDER BY rental_id DESC LIMIT 1").executeQuery();
    }

    @Override
    public void updateRental(Rental updateRental) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("UPDATE rental SET customer_id = ? ,book_id = ? ,issue_date = ? ,due_date = ? ,return_date = ? ,fine = ? WHERE rental_id =?");
        psTm.setObject(1,updateRental.getCustomerId());
        psTm.setObject(2,updateRental.getBookId());
        psTm.setObject(3,updateRental.getIssueDate());
        psTm.setObject(4,updateRental.getDueDate());
        psTm.setObject(5,updateRental.getReturnDate());
        psTm.setObject(6,updateRental.getFine());
        psTm.setObject(7,updateRental.getRentalId());

        psTm.executeUpdate();
    }

    @Override
    public ResultSet searchRental(String rentalId, String bookId) throws SQLException {
        String SQL = "SELECT * FROM rental WHERE rental_id = ? OR book_id= ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,rentalId);
        psTm.setObject(2,bookId);
        ResultSet resultSet = psTm.executeQuery();
        return resultSet;
    }

    @Override
    public void deleteRentalr(String rentalId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("DELETE FROM customer WHERE customer_id =?");
        psTm.setObject(1,rentalId);

        psTm.executeUpdate();
    }

    @Override
    public boolean returnBook(String rentalId, String bookId, double fine) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("UPDATE rental SET return_date = CURDATE(), fine =? WHERE rental_id =?");
        psTm.setObject(1,fine);
        psTm.setObject(2,rentalId);
        return psTm.executeUpdate()>0;
    }
}
