package edu.RL.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.User;
import edu.RL.service.Service.UserService;
import edu.RL.service.UserServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblForgotPwd;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblsignin;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtPassword;

    UserService userService = new UserServiceImpl();

    @FXML
    void btnSignInOnAction(ActionEvent event) throws SQLException {
        try {

            User user = userService.login(txtUsername.getText(),txtPassword.getText());
            if(user != null) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDashboard.fxml"));
                Parent root = loader.load();
                userDashboardController controller = loader.getController();
                controller.setLoggedUser(user);

                Stage stage = (Stage) txtUsername.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.centerOnScreen();

            }

            else {
                new Alert(Alert.AlertType.ERROR, "Invalid username or password").show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void forgotPasswordClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
