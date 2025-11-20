package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.Rental;
import edu.RL.service.Service.RentalService;
import edu.RL.service.RentalServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RentalsAndReturnsFormController implements Initializable {

    RentalService rentalService = new RentalServiceImpl();

    @FXML
    private JFXButton btnAddDetails;

    @FXML
    private JFXButton btnDeleteDetails;

    @FXML
    private JFXButton btnSearchDetails;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private TableColumn<?, ?> colBookID;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colFine;

    @FXML
    private TableColumn<?, ?> colIssueDate;

    @FXML
    private TableColumn<?, ?> colRentalID;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private JFXTextField txtRentalId;

    @FXML
    private TableView<Rental> tableviewRentals;;

    @FXML
    private JFXTextField txtbookId;

    @FXML
    private JFXTextField txtcusId;

    @FXML
    private JFXTextField txtFine;

    @FXML
    private JFXTextField txtDueDate;

    @FXML
    private JFXTextField txtIssueDate;

    @FXML
    private JFXTextField txtReturnDate;


    @FXML
    void btnAddDetailsOnAction(ActionEvent event) {
        try {
            if(!rentalService.isAvailable(txtbookId.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "This book is currently not available");
                alert.show();
                return;
            }
            if (!rentalService.canBorrow(txtcusId.getText(), txtbookId.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "This customer has already borrowed this book and not returned it!");
                alert.show();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        LocalDate issueDate = LocalDate.now();

        LocalDate dueDate = issueDate.plusDays(1);

        rentalService.addRental(new Rental(
                txtRentalId.getText(),
                txtcusId.getText(),
                txtbookId.getText(),
                issueDate,
                dueDate,
                null,
                0.0
        ));
        loadRenatlTable();

        new Alert(Alert.AlertType.INFORMATION, "Rental created successfully!").show();
    }
    @FXML
    void btnUpdateDetailsOnAction(ActionEvent event) {
        LocalDate issueDate = LocalDate.now();

        LocalDate dueDate = issueDate.plusDays(7);
        rentalService.updateRental(new Rental(
                txtRentalId.getText(),
                txtcusId.getText(),
                txtbookId.getText(),
                issueDate,
                dueDate,
                null,
                0.0
        ));
        loadRenatlTable();
    }

    @FXML
    void btnDeleteDetailsOnAction(ActionEvent event) {
        rentalService.deleteRental(txtRentalId.getText());
        loadRenatlTable();
    }

    @FXML
    void btnSearchDetailsOnAction(ActionEvent event) {
        Rental searchRental = rentalService.searchRental(txtRentalId.getText(), txtbookId.getText());
        txtcusId.setText(searchRental.getCustomerId());
        txtIssueDate.setText(String.valueOf(searchRental.getIssueDate()));
        txtDueDate.setText(String.valueOf(searchRental.getDueDate()));
        txtReturnDate.setText(String.valueOf(searchRental.getReturnDate()));
        txtFine.setText(String.valueOf(searchRental.getFine()));
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        double fine = rentalService.returnBook(txtRentalId.getText(),txtbookId.getText());
        txtFine.setText(String.valueOf(fine));
        loadRenatlTable();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRentalID.setCellValueFactory(new PropertyValueFactory<>("rentalId"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));

        txtIssueDate.setEditable(false);
        txtIssueDate.setDisable(false);

        txtDueDate.setEditable(false);
        txtDueDate.setDisable(false);

        txtReturnDate.setEditable(false);
        txtReturnDate.setDisable(false);

        txtFine.setEditable(false);
        txtFine.setDisable(false);

        txtIssueDate.setText(LocalDate.now().toString());
        txtDueDate.setText(LocalDate.now().plusDays(1).toString());

        tableviewRentals.getSelectionModel().selectedItemProperty().addListener((((observableValue, oldValue, newValue) -> {
            if(null!=newValue){
                setSelectedCustomer((Rental) newValue);
            }
        })));

        loadRenatlTable();

        setNextId();
    }

    private void setNextId() {
        String nextId = null;
        try {
            nextId = rentalService.generateNextRentalId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtRentalId.setText(nextId);
    }

    private void loadRenatlTable() {
        tableviewRentals.setItems(rentalService.getAll());
    }

    private void setSelectedCustomer(Rental rental) {

        txtRentalId.setText(rental.getRentalId());
        txtcusId.setText(rental.getCustomerId());
        txtbookId.setText(rental.getBookId());
        txtIssueDate.setText(String.valueOf(rental.getIssueDate()));
        txtDueDate.setText(String.valueOf(rental.getDueDate()));
        if (rental.getReturnDate() == null) {
            txtReturnDate.setText(LocalDate.now().toString());

            double fine = rentalService.calculateFine(rental);
            txtFine.setText(String.valueOf(fine));
        } else {
            // Book already returned, show stored details
            txtReturnDate.setText(rental.getReturnDate().toString());
            txtFine.setText(String.valueOf(rental.getFine()));
        }
    }
}


