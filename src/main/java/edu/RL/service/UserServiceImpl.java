package edu.RL.service;

import edu.RL.dto.Customer;
import edu.RL.dto.Rental;
import edu.RL.dto.User;
import edu.RL.repository.UserRepository;
import edu.RL.repository.UserRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
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
                                resultSet.getString("status"),
                                resultSet.getString("email")
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
            throw new RuntimeException("Failed to generate user Id", e);
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
    public void updateUser(String userId, String username, String role, String status, String email) {
        try {
            userRepository.updateUser(userId,username,role,status,email);
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

    @Override
    public User findByUsername(String username) {
        try {
            ResultSet resultSet = userRepository.findByUsername(username);
            resultSet.next();
            return new User(
                    resultSet.getString("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"),
                    resultSet.getString("status"),
                    resultSet.getString("email")
            );
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This User is not in DataBase");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User login(String username, String password) throws SQLException {
        ResultSet resultSet = userRepository.findByUsername(username);
        if (!resultSet.next()) {
            return null;
        }

        User user = new User(
                resultSet.getString("user_id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("role"),
                resultSet.getString("status"),
                resultSet.getString("email")
        );

        if (BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            ResultSet resultSet = userRepository.findByEmail(email);
            resultSet.next();
            return new User(
                    resultSet.getString("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"),
                    resultSet.getString("status"),
                    resultSet.getString("email")
            );
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This User is not in DataBase");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveOTP(String email, String otp) {
        try {
            return userRepository.saveOTP(email,otp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        try {
            return userRepository.verifyOTP(email, otp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatePassword(Connection connection,String email, String newPassword) {
        try {
            return userRepository.updatePassword(connection,email, newPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteOTP(Connection connection,String email) {
        try {
            return userRepository.deleteOTP(connection,email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}


//        ResultSet resultSet = userRepository.login(username,password);
//        try {
//            if (resultSet.next()) {
//                return new User(
//                        resultSet.getString("user_id"),
//                        resultSet.getString("username"),
//                        resultSet.getString("password"),
//                        resultSet.getString("role"),
//                        resultSet.getString("status")
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null;


