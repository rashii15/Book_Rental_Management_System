package edu.RL.service.Service;

import edu.RL.dto.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerService {
    ObservableList<Customer> getAll();

    String generateNextBookId() throws SQLException;

    void addCustomer(Customer customer);

    void updateCustomer(Customer updateCustomer);

    Customer searchCustomer(String cusId, String name);

    void deleteCustomer(String cusId);

    int getCustomerCount();
}
