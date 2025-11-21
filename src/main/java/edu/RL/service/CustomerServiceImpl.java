package edu.RL.service;

import edu.RL.dto.Customer;
import edu.RL.repository.Repository.CustomerRepository;
import edu.RL.repository.CustomerRepositoryImpl;
import edu.RL.service.Service.CustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository = new CustomerRepositoryImpl();

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = customerRepository.getAll();
            while (resultSet.next()) {
                customerObservableList.add(new Customer(
                        resultSet.getString("customer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact"),
                        resultSet.getString("email"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getString("address"),
                        resultSet.getString("postal_code"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerObservableList;
    }

    @Override
    public String generateNextBookId() throws SQLException {
        ResultSet resultSet = customerRepository.getNextId();
        try {
            if (resultSet.next()) {
                String lastId = resultSet.getString("customer_id");
                int idNum = Integer.parseInt(lastId.substring(1));
                idNum++;
                return String.format("C%03d", idNum);
            } else {
                return "C001";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to generate customer Id",e);
        }
    }

    @Override
    public void addCustomer(Customer newcustomer) {
        try {
            customerRepository.addCustomer(newcustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCustomer(Customer updateCustomer) {
        try {
            customerRepository.updateCustomer(updateCustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String cusId, String name) {
        try {
            ResultSet resultSet = customerRepository.searchCustomer(cusId, name);
            resultSet.next();
            return new Customer(
                    resultSet.getString("customer_id"),
                    resultSet.getString("name"),
                    resultSet.getString("contact"),
                    resultSet.getString("email"),
                    resultSet.getDate("DOB").toLocalDate(),
                    resultSet.getString("address"),
                    resultSet.getString("postal_code")
            );
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This customerID is not in DataBase");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomer(String cusId) {
        try {
            customerRepository.deleteCustomer(cusId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCustomerCount() {
        try {
            return customerRepository.getCustomerCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
