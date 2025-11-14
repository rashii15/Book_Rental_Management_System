package edu.RL.service;

import edu.RL.dto.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface UserService {

    ObservableList<User> getAll();

    String generateNextUserId() throws SQLException;

    void addUser(User addUser);

    void updateUser(User updateUser);

    void deleteUser(String userId);
}
