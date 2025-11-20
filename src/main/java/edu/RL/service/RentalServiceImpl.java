package edu.RL.service;

import edu.RL.dto.Rental;
import edu.RL.repository.Repository.BooksRepository;
import edu.RL.repository.BooksRepositoryImpl;
import edu.RL.repository.Repository.RentalRepository;
import edu.RL.repository.RentalRepositoryImpl;
import edu.RL.service.Service.RentalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalServiceImpl implements RentalService {

    RentalRepository rentalRepository = new RentalRepositoryImpl();
    BooksRepository booksRepository =  new BooksRepositoryImpl();

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
                        resultSet.getDate("return_date") == null ? null : resultSet.getDate("return_date").toLocalDate(),
                        resultSet.getDouble("fine"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rentalObservableList;
    }

    @Override
    public boolean addRental(Rental newRental) {
        try {
            boolean isadded = rentalRepository.addRental(newRental);

            if (isadded){
                booksRepository.reduceAvailableCopies(newRental.getBookId());
            }
            return isadded;
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

    @Override
    public Rental searchRental(String RentalId, String bookId) {
        try {
            ResultSet resultSet = rentalRepository.searchRental(RentalId, bookId);
            resultSet.next();
                return new Rental(
                    resultSet.getString("rental_id"),
                    resultSet.getString("customer_id"),
                    resultSet.getString("book_id"),
                    resultSet.getDate("issue_date").toLocalDate(),
                    resultSet.getDate("return_date").toLocalDate(), resultSet.getDate("due_date").toLocalDate(),
                    resultSet.getDouble("fine")
            );
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This customerID is not in DataBase");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRental(String rentalId) {
        try {
            rentalRepository.deleteRentalr(rentalId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isAvailable(String bookId) throws SQLException {
        return booksRepository.isAvailable(bookId);
    }

    @Override
    public double returnBook(String rentalId, String bookId) {
        try {
            ResultSet resultSet = rentalRepository.searchRental(rentalId, bookId);
            resultSet.next();
            Rental rental = new Rental(
                    resultSet.getString("rental_id"),
                    resultSet.getString("customer_id"),
                    resultSet.getString("book_id"),
                    resultSet.getDate("issue_date").toLocalDate(),
                    resultSet.getDate("return_date") == null ? null : resultSet.getDate("return_date").toLocalDate(), resultSet.getDate("due_date").toLocalDate(),
                    resultSet.getDouble("fine")
            );

            LocalDate returnDate = LocalDate.now();
            double fine = calculateFine(rental);
            rental.setReturnDate(returnDate);
            rental.setFine(fine);
            rentalRepository.updateRental(rental);

            boolean isReturned = rentalRepository.returnBook(rentalId,bookId,fine);

            if (isReturned){
                booksRepository.increaseAvailableCopies(bookId);
            }
            return fine;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double calculateFine(Rental rental) {
        if (rental.getReturnDate() != null) {
            return rental.getFine();
        }

            LocalDate dueDate = rental.getDueDate();
            LocalDate returnDate = LocalDate.now();
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            double fine = 0;
            if (daysLate > 0) {
                if (daysLate <= 30) {
                    fine = daysLate * 20;
                } else {
                    fine = (30 * 20) + ((daysLate - 30) * 50);
                }
            }
            return fine;
    }

    @Override
    public boolean canBorrow(String cusID, String bookId) throws SQLException {
        ResultSet resultSet =  rentalRepository.canBorrow(cusID,bookId);
        try {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public int getRentalCount() {
        try {
            return rentalRepository.getRentalCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
