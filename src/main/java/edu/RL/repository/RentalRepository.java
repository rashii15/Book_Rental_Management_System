package edu.RL.repository;

import edu.RL.dto.Rental;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RentalRepository {
    ResultSet getAll() throws SQLException;

    void addRental(Rental newRental) throws SQLException;

    ResultSet getNextId() throws SQLException;

    void updateRental(Rental updateRental) throws SQLException;
}
