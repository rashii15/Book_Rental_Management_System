package edu.RL.service;

import edu.RL.dto.User;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserService {

    ObservableList<User> getAll();

    String generateNextUserId() throws SQLException;

    void addUser(User addUser);

    void deleteUser(String userId);

    User findByUsername(String username);

    User login(String username, String password) throws SQLException;

    User findByEmail(String email);

    boolean saveOTP(String email, String otp);

    boolean verifyOTP(String email, String otp);

    boolean updatePassword(Connection connection,String email, String newPassword);

    boolean deleteOTP(Connection connection,String email);

    void updateUser(String userId, String username, String role, String status, String email);
}
