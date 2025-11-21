package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.User;
import edu.RL.service.Service.UserService;
import edu.RL.service.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mindrot.jbcrypt.BCrypt;

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
    private JFXButton btnRefresh;

    @FXML
    private TableColumn<?, ?> colEmail;


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
    private JFXTextField txtUserId;

    @FXML
    private TableView<User> tableviewUsers;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    void btnAddUserOnAction(ActionEvent event) {

        String hashedPassword = BCrypt.hashpw(txtPassword.getText(), BCrypt.gensalt());
        userService.addUser(new User(
                txtUserId.getText(),
                txtUsername.getText(),
                hashedPassword,
                comboboxRole.getValue(),
                comboboxStatus.getValue(),
                txtEmail.getText()
        ));
        loadUserTable();
        clearFields();
    }

    @FXML
    void btnSearchUserOnAction(ActionEvent event) {
        User searchUser = userService.searchUser(txtUserId.getText(), txtUsername.getText());
        comboboxRole.setValue(searchUser.getRole());
        comboboxStatus.setValue(searchUser.getStatus());
    }

    @FXML
    void btnDeleteUserOnAction(ActionEvent event) {
        userService.deleteUser(txtUserId.getText());
        loadUserTable();
        clearFields();
    }

    @FXML
    void btnUpdateUserOnAction(ActionEvent event) {
        userService.updateUser(
                txtUserId.getText(),
                txtUsername.getText(),
                comboboxRole.getValue(),
                comboboxStatus.getValue(),
                txtEmail.getText()
        );
        loadUserTable();
        clearFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        comboboxRole.setItems(FXCollections.observableArrayList("ADMIN", "STAFF"));
        comboboxStatus.setItems(FXCollections.observableArrayList("ACTIVE", "INACTIVE"));

        tableviewUsers.getSelectionModel().selectedItemProperty().addListener((((observableValue, oldValue, newValue) -> {
            if(null!=newValue){
                setSelectedItem((User) newValue);
            }
        })));

        loadUserTable();

        setNextId();
    }

    private void setSelectedItem(User user) {
        txtUserId.setText(user.getUserId());
        txtUsername.setText(user.getUsername());
        txtPassword.setVisible(false);
        comboboxRole.setValue(user.getRole());
        comboboxStatus.setValue(user.getStatus());
        txtEmail.setText(user.getEmail());
    }

    private void setNextId() {
        String nextId = null;
        try {
            nextId = userService.generateNextUserId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtUserId.setText(nextId);
    }


    private void loadUserTable() {
        tableviewUsers.setItems(userService.getAll());
    }
    private void clearFields() {
        setNextId();
        txtUsername.setText("");
        txtEmail.setText("");
        comboboxRole.setValue("");
        comboboxStatus.setValue("");
        txtPassword.setVisible(true);
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        clearFields();
    }


}
