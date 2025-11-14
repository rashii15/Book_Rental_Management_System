package edu.RL.service;

import edu.RL.dto.Customer;
import edu.RL.dto.Rental;
import edu.RL.repository.RentalRepository;
import edu.RL.repository.RentalRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RentalServiceImpl implements RentalService{

    RentalRepository rentalRepository = new RentalRepositoryImpl();

    @Override
    public ObservableList<Rental> getAll() {
        ObservableList<Rental> rentalObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = rentalRepository.getAll();
            while (resultSet.next()) {
                rentalObservableList.add(new Rental(
                        resultSet.getString("rental_id"),
                        resultSet.getString("customer_id"),
                        resultSet.getString("book_id"),
                        resultSet.getDate("issue_date").toLocalDate(),
                        resultSet.getDate("due_date").toLocalDate(),
                        resultSet.getDate("return_date").toLocalDate(),
                        resultSet.getDouble("fine"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rentalObservableList;
    }

    @Override
    public void addRental(Rental newRental) {
        try {
            rentalRepository.addRental(newRental);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateNextRentalId() throws SQLException {
        ResultSet resultSet = rentalRepository.getNextId();
        try {
            if (resultSet.next()) {
                String lastId = resultSet.getString("rental_id");
                int idNum = Integer.parseInt(lastId.substring(1));
                idNum++;
                return String.format("R%03d", idNum);
            } else {
                return "R001";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to generate rental Id",e);
        }
    }

    @Override
    public void updateRental(Rental updateRental) {
        try {
            rentalRepository.updateRental(updateRental);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
