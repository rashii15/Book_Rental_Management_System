package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.Customer;
import edu.RL.dto.Rental;
import edu.RL.service.RentalService;
import edu.RL.service.RentalServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RentalsAndReturnsFormController implements Initializable {

    RentalService rentalService = new RentalServiceImpl();

    @FXML
    private DatePicker DateDuedate;

    @FXML
    private DatePicker DateIssuedate;

    @FXML
    private DatePicker DateReturndate;

    @FXML
    private JFXButton btnAddDetails;

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
    private Label lblRentalId;

    @FXML
    private TableView<Rental> tableviewRentals;;

    @FXML
    private JFXTextField txtbookId;

    @FXML
    private JFXTextField txtcusId;

    @FXML
    private JFXTextField txtFine;

    @FXML
    void btnAddDetailsOnAction(ActionEvent event) {
        rentalService.addRental(new Rental(
                lblRentalId.getText(),
                txtcusId.getText(),
                txtbookId.getText(),
                DateIssuedate.getValue(),
                DateDuedate.getValue(),
                DateReturndate.getValue(),
                Double.parseDouble(txtFine.getText())
        ));
        loadRenatlTable();
    }
    @FXML
    void btnUpdateDetailsOnAction(ActionEvent event) {
        rentalService.updateRental(new Rental(
                lblRentalId.getText(),
                txtcusId.getText(),
                txtbookId.getText(),
                DateIssuedate.getValue(),
                DateDuedate.getValue(),
                DateReturndate.getValue(),
                Double.parseDouble(txtFine.getText())
        ));
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
        lblRentalId.setText(nextId);
    }

    private void loadRenatlTable() {
        tableviewRentals.setItems(rentalService.getAll());
    }

    private void setSelectedCustomer(Rental rental) {
        lblRentalId.setText(rental.getRentalId());
        txtcusId.setText(rental.getCustomerId());
        txtbookId.setText(rental.getBookId());
        DateIssuedate.setValue(rental.getIssueDate());
        DateDuedate.setValue(rental.getDueDate());
        DateReturndate.setValue(rental.getReturnDate());
        txtFine.setText(String.valueOf(rental.getFine()));
    }
}


