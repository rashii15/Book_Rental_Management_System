package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class userDashboardController {


    @FXML
    private JFXButton btnMngBooks;

    @FXML
    private JFXButton btnMngCustomers;

    @FXML
    private JFXButton btnMngRentals;

    @FXML
    private JFXButton btnMngRentals1;

    @FXML
    private Label lbldashboard;

    @FXML
    private Label lblmenu;

    @FXML
    private Pane contentArea;

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
