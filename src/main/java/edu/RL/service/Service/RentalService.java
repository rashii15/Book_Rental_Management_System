package edu.RL.service.Service;

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

    double returnBook(String rentalId, String bookId);

    double calculateFine(Rental rental);

    boolean canBorrow(String cusID, String bookId) throws SQLException;

    int getRentalCount();
}
