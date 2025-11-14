package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.Rental;
import edu.RL.dto.User;
import edu.RL.service.UserService;
import edu.RL.service.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    UserService userService = new UserServiceImpl();

    @FXML
    private JFXButton btnAddUser;

    @FXML
    private JFXButton btnDeleteUser;

    @FXML
    private JFXButton btnUpdateUser;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private JFXComboBox<String> comboboxRole;

    @FXML
    private JFXComboBox<String> comboboxStatus;

    @FXML
    private Label lblUserId;

    @FXML
    private TableView<User> tableviewUsers;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void initialize(){
        comboboxRole.setItems(FXCollections.observableArrayList("ADMIN", "STAFF"));
        comboboxStatus.setItems(FXCollections.observableArrayList("ACTIVE", "INACTIVE"));
    }

    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        userService.addUser(new User(
                lblUserId.getText(),
                txtUsername.getText(),
                txtPassword.getText(),
                comboboxRole.getValue(),
                comboboxStatus.getValue()
        ));
        loadUserTable();
    }

    @FXML
    void btnDeleteUserOnAction(ActionEvent event) {
        userService.deleteUser(lblUserId.getText());
        loadUserTable();
    }

    @FXML
    void btnUpdateUserOnAction(ActionEvent event) {
        userService.updateUser(new User(
                lblUserId.getText(),
                txtUsername.getText(),
                txtPassword.getText(),
                comboboxRole.getValue(),
                comboboxStatus.getValue()
        ));
        loadUserTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadUserTable();

        setNextId();
    }

    private void setNextId() {
        String nextId = null;
        try {
            nextId = userService.generateNextUserId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblUserId.setText(nextId);
    }


    private void loadUserTable() {
        tableviewUsers.setItems(userService.getAll());
    }


}
