package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.TouchEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    Stage UserDashboardStage = new Stage();

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblForgotPwd;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblsignin;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        try {
            UserDashboardStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserDashboardStage.show();;
    }


    @FXML
    void forgotpasswordlink(TouchEvent event) {

    }

}
