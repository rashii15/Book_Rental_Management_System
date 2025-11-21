package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.service.Service.ResetPasswordService;
import edu.RL.service.ResetPasswordServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ResetPasswordController {
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    private JFXButton btnResetPassword;

    @FXML
    private JFXTextField txtConfirmPassword;

    @FXML
    private JFXTextField txtNewPassword;

    ResetPasswordService resetPasswordService = new ResetPasswordServiceImpl();

    @FXML
    void btnResetPasswordOnAction(ActionEvent event) throws IOException {

        try {
            resetPasswordService.resetPassword(txtNewPassword.getText(),txtConfirmPassword.getText(),email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) txtNewPassword.getScene().getWindow();
        stage.setScene(new Scene(root));


    }
}
