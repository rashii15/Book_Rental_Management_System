package edu.RL.service;

import edu.RL.db.DBConnection;
import edu.RL.dto.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;

public class ResetPasswordServiceImpl implements ResetPasswordService{

    UserService userService =  new UserServiceImpl();

    @Override
    public void resetPassword(String newpassword, String confirmpassword, String email) throws SQLException {
        String hashedPassword = BCrypt.hashpw(newpassword, BCrypt.gensalt());
        Connection connection = DBConnection.getInstance().getConnection();
        if (!newpassword.equals(confirmpassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return;
        }

        try {
            connection.setAutoCommit(false);

            boolean isUpdate = userService.updatePassword(connection,email, hashedPassword);
            System.out.println("Password updated? " + isUpdate);
            boolean isDelete = userService.deleteOTP(connection,email);
            System.out.println("OTP deleted? " + isDelete);
            if (isUpdate && isDelete){

                connection.commit();
                new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();
            }else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Password reset failed!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }

    }
}
