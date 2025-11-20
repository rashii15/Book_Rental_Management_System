package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import edu.RL.dto.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class userDashboardController {

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnMngBooks;

    @FXML
    private JFXButton btnMngCustomers;

    @FXML
    private JFXButton btnMngRentals;

    @FXML
    private JFXButton btnMngUsers;

    @FXML
    private Label lbldashboard;

    @FXML
    private Label lblmenu;

    @FXML
    private Pane contentArea;

    private User loggedUser;

    public void setLoggedUser(User user) {
        this.loggedUser = user;
        applyRoleAccess();
    }

    private void applyRoleAccess() {
        if (loggedUser.getRole().equals("STAFF")) {
            btnMngUsers.setVisible(false);
        }
    }

    @FXML
    void initialize(){
        btnDashboardOnAction();
    }

    @FXML
    void btnMngBooks(ActionEvent event) {
        loadUI("ManageBooks.fxml");
    }

    @FXML
    void btnMngCustomers(ActionEvent event) {
        loadUI("ManageCustomers.fxml");
    }

    @FXML
    void btnMngRentals(ActionEvent event) {
        loadUI("ManageRentals&Returns.fxml");
    }

    @FXML
    void btnMngUsers(ActionEvent event) {
        loadUI("ManageUsers.fxml");
    }

    @FXML
    void btnDashboardOnAction() {

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to logout?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) btnLogout.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        }
    }

    private void loadUI(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/"+fxmlFile));
            contentArea.getChildren().clear();

            contentArea.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
