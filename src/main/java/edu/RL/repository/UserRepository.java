package edu.RL.repository;

import edu.RL.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserRepository {
    ResultSet getAll() throws SQLException;

    ResultSet getNextId() throws SQLException;

    void addUser(User addUser) throws SQLException;

    void updateUser(User updateUser) throws SQLException;

    void deleteUser(String userId) throws SQLException;
}
