package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.User;
import edu.RL.service.UserService;
import edu.RL.service.UserServiceImpl;
import edu.RL.util.EmailUtil;
import edu.RL.util.OTPUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ForgotPasswordController {

    UserService userService = new UserServiceImpl();

    @FXML
    private JFXButton btnSendOTP;

    @FXML
    private JFXTextField txtEmail;


    @FXML
    void btnSendOTPOnAction(ActionEvent event) throws Exception {
        String email = txtEmail.getText();

        // 1. Check if email exists in database
        User user = userService.findByEmail(email);

        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Email not found!").show();
            return;
        }

        // 2. Generate OTP
        String otp = OTPUtil.generateOTP();

        // 3. Save OTP temporarily in database
        userService.saveOTP(email, otp);

        // 4. Send OTP to email
        EmailUtil.sendOTP(email, otp);

        // 5. Proceed to OTP verification screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyOTP.fxml"));
        Parent root = loader.load();

        verifyOTPController controller = loader.getController();
        controller.setEmail(email);

        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
