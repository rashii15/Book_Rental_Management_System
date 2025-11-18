package edu.RL.service;

import edu.RL.dto.Rental;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface RentalService {

    ObservableList<Rental> getAll();

    boolean addRental(Rental newRental);

    String generateNextRentalId() throws SQLException;

    void updateRental(Rental updateRental);

    Rental searchRental(String RentalId, String bookId);

    void deleteRental(String rentalId);

    boolean isAvailable(String bookId) throws SQLException;

    boolean returnBook(String rentalId, String bookId);
}
