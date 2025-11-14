package edu.RL.service;

import edu.RL.dto.Rental;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface RentalService {

    ObservableList<Rental> getAll();

    void addRental(Rental newRental);

    String generateNextRentalId() throws SQLException;

    void updateRental(Rental updateRental);
}
