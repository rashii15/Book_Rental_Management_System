package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.service.UserService;
import edu.RL.service.UserServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class verifyOTPController {
    UserService userService = new UserServiceImpl();

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }
    @FXML
    private JFXButton btnVerifyOTP;

    @FXML
    private JFXTextField txtOTP;

    @FXML
    void btnVerifyOTPOnAction(ActionEvent event) throws IOException {
        boolean isValid = userService.verifyOTP(email, txtOTP.getText());

        if (!isValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid or expired OTP").show();
            return;
        }

        // OTP correct → open ResetPassword screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPassword.fxml"));
        Parent root = loader.load();

        ResetPasswordController controller = loader.getController();
        controller.setEmail(email);

        Stage stage = (Stage) txtOTP.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
