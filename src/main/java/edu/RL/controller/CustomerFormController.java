package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.Customer;
import edu.RL.service.Service.CustomerService;
import edu.RL.service.CustomerServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {


    CustomerService customerService = new CustomerServiceImpl();

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private JFXButton btnUpdateCustomer;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> colemail;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colpostalCode;

    @FXML
    private TableView<Customer> tableviewCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    public DatePicker datePickerDOB;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        customerService.addCustomer(new Customer(
                txtCustomerId.getText(),
                txtName.getText(),
                txtContact.getText(),
                txtEmail.getText(),
                datePickerDOB.getValue(),
                txtAddress.getText(),
                txtPostalCode.getText()
        ));
        loadCustomerTable();
        clearFields();
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        customerService.deleteCustomer(txtCustomerId.getText());
        loadCustomerTable();
        clearFields();
    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        customerService.updateCustomer(new Customer(
                txtCustomerId.getText(),
                txtName.getText(),
                txtContact.getText(),
                txtEmail.getText(),
                datePickerDOB.getValue(),
                txtAddress.getText(),
                txtPostalCode.getText()));

        loadCustomerTable();
        clearFields();
    }

    @FXML
    void btnSearchCustomerOnAction(ActionEvent event) {
        Customer searchCustomer = customerService.searchCustomer(txtCustomerId.getText(), txtName.getText());
        txtContact.setText(searchCustomer.getContact());
        txtEmail.setText(searchCustomer.getEmail());
        datePickerDOB.setValue(searchCustomer.getDOB());
        txtAddress.setText(searchCustomer.getAddress());
        txtPostalCode.setText(searchCustomer.getPostalCode());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colpostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        tableviewCustomers.getSelectionModel().selectedItemProperty().addListener((((observableValue, oldValue, newValue) -> {
            if(null!=newValue){
                setSelectedCustomer((Customer) newValue);
            }
        })));

        loadCustomerTable();

        setNextId();
    }

    private void setNextId() {
        String nextId = null;
        try {
            nextId = customerService.generateNextBookId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtCustomerId.setText(nextId);
    }

    private void loadCustomerTable() {
        tableviewCustomers.setItems(customerService.getAll());
    }

    private void setSelectedCustomer(Customer customer) {
        txtCustomerId.setText(customer.getCustomerId());
        txtName.setText(customer.getCusName());
        txtContact.setText(customer.getContact());
        txtEmail.setText(customer.getEmail());
        datePickerDOB.setValue(customer.getDOB());
        txtAddress.setText(customer.getAddress());
        txtPostalCode.setText(customer.getPostalCode());
    }

    private void clearFields() {
        setNextId();
        txtName.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        datePickerDOB.setValue(null);
        txtAddress.setText("");
        txtPostalCode.setText("");
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        clearFields();
    }
}
