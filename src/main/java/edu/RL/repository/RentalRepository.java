package edu.RL.repository;

import edu.RL.dto.Rental;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RentalRepository {
    ResultSet getAll() throws SQLException;

    boolean addRental(Rental newRental) throws SQLException;

    ResultSet getNextId() throws SQLException;

    void updateRental(Rental updateRental) throws SQLException;

    ResultSet searchRental(String rentalId, String bookId) throws SQLException;

    void deleteRentalr(String rentalId) throws SQLException;

    boolean returnBook(String rentalId, String bookId, double fine) throws SQLException;

    ResultSet canBorrow(String cusID, String bookId) throws SQLException;
}
