package edu.RL.repository;

import edu.RL.db.DBConnection;
import edu.RL.dto.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepositoryImpl implements CustomerRepository{
    @Override
    public ResultSet getAll() throws SQLException {
        return DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customer").executeQuery();
    }

    @Override
    public ResultSet getNextId() throws SQLException {
        return DBConnection.getInstance().getConnection().prepareStatement("SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1").executeQuery();
    }

    @Override
    public void addCustomer(Customer newcustomer) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("INSERT INTO customer Values(? ,? ,? ,? ,? ,? ,?)");
        psTm.setObject(1,newcustomer.getCustomerId());
        psTm.setObject(2,newcustomer.getCusName());
        psTm.setObject(3,newcustomer.getContact());
        psTm.setObject(4,newcustomer.getEmail());
        psTm.setObject(5,newcustomer.getDOB());
        psTm.setObject(6,newcustomer.getAddress());
        psTm.setObject(7,newcustomer.getPostalCode());

        psTm.executeUpdate();
    }

    @Override
    public void updateCustomer(Customer updateCustomer) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("UPDATE customer SET name = ? ,contact = ? ,email = ? ,DOB = ? ,address = ? ,postal_code = ? WHERE customer_id =?");
        psTm.setObject(1,updateCustomer.getCusName());
        psTm.setObject(2,updateCustomer.getContact());
        psTm.setObject(3,updateCustomer.getEmail());
        psTm.setObject(4,updateCustomer.getDOB());
        psTm.setObject(5,updateCustomer.getAddress());
        psTm.setObject(6,updateCustomer.getPostalCode());
        psTm.setObject(7,updateCustomer.getCustomerId());

        psTm.executeUpdate();
    }
}
