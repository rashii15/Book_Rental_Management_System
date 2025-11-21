package edu.RL.repository.Repository;

import edu.RL.dto.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerRepository {
    ResultSet getAll() throws SQLException;

    ResultSet getNextId() throws SQLException;

    void addCustomer(Customer newcustomer) throws SQLException;

    void updateCustomer(Customer updateCustomer) throws SQLException;

    ResultSet searchCustomer(String cusId, String name) throws SQLException;

    void deleteCustomer(String cusId) throws SQLException;

    int getCustomerCount() throws SQLException;
}
