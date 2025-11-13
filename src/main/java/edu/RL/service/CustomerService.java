package edu.RL.service;

import edu.RL.dto.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerService {
    ObservableList<Customer> getAll();

    String generateNextBookId() throws SQLException;

    void addCustomer(Customer customer);

    void updateCustomer(Customer updateCustomer);
}
