package edu.RL.service;

import edu.RL.dto.Rental;
import edu.RL.dto.User;
import edu.RL.repository.UserRepository;
import edu.RL.repository.UserRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public ObservableList<User> getAll() {
        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = userRepository.getAll();
            while (resultSet.next()) {
                userObservableList.add(new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("role")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userObservableList;
    }

    @Override
    public String generateNextUserId() throws SQLException {
        ResultSet resultSet = userRepository.getNextId();
        try {
            if (resultSet.next()) {
                String lastId = resultSet.getString("user_id");
                int idNum = Integer.parseInt(lastId.substring(1));
                idNum++;
                return String.format("U%03d", idNum);
            } else {
                return "U001";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to generate user Id",e);
        }
    }

    @Override
    public void addUser(User addUser) {
        try {
            userRepository.addUser(addUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(User updateUser) {
        try {
            userRepository.updateUser(updateUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(String userId) {
        try {
            userRepository.deleteUser(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
