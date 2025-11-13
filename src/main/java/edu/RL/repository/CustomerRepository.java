package edu.RL.repository;

import edu.RL.dto.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerRepository {
    ResultSet getAll() throws SQLException;

    ResultSet getNextId() throws SQLException;

    void addCustomer(Customer newcustomer) throws SQLException;

    void updateCustomer(Customer updateCustomer) throws SQLException;
}
