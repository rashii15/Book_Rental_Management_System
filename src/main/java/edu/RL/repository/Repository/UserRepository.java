package edu.RL.repository.Repository;

import edu.RL.dto.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserRepository {
    ResultSet getAll() throws SQLException;

    ResultSet getNextId() throws SQLException;

    void addUser(User addUser) throws SQLException;

    void deleteUser(String userId) throws SQLException;

    ResultSet login(String username, String password) throws SQLException;

    ResultSet findByUsername(String username) throws SQLException;

    ResultSet findByEmail(String email) throws SQLException;

    boolean saveOTP(String email, String otp) throws SQLException;

    boolean verifyOTP(String email, String otp) throws SQLException;

    boolean updatePassword(Connection connection,String email, String hashed) throws SQLException;

    boolean deleteOTP(Connection connection,String email) throws SQLException;

    void updateUser(String userId, String username, String role, String status, String email) throws SQLException;

    ResultSet getDailyRentalReport() throws SQLException;

    ResultSet searchUser(String userId, String userName) throws SQLException;
}
