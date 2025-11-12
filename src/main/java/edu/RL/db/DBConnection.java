package edu.RL.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() throws SQLException {
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/book_rental_management_system","root","12345678");
    }

    public static DBConnection getInstance() throws SQLException {
        return null==instance?instance=new DBConnection():instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

