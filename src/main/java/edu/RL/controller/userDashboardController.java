package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import edu.RL.dto.User;
import edu.RL.service.*;
import edu.RL.service.Service.BooksService;
import edu.RL.service.Service.CustomerService;
import edu.RL.service.Service.RentalService;
import edu.RL.service.Service.UserService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.RL.dto.RentalReportDTO;
import edu.RL.util.DailyReportPDF;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ResourceBundle;


public class userDashboardController implements Initializable {
    UserService userService = new UserServiceImpl();
    BooksService booksService = new BooksServiceImpl();
    CustomerService customerService = new CustomerServiceImpl();
    RentalService rentalService = new RentalServiceImpl();

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
    private JFXButton btngenerateReport;

    @FXML
    private JFXButton btndashboard;

    @FXML
    private Label lbldashboard;

    @FXML
    private Label lblmenu;

    @FXML
    private Label lblBookCount;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private Label lblRentalCount;

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
            btngenerateReport.setVisible(false);
        }
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
        try {
            Stage stage = (Stage) btndashboard.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    void btnGenerateReportOnAction(ActionEvent event) {
        try {
            List<RentalReportDTO> data = userService.getDailyRentalReport();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("DailyRentalReport.pdf");
            File file = fileChooser.showSaveDialog(null);
            if(file != null){
                DailyReportPDF pdf = new DailyReportPDF();
                pdf.generate(data, file.getAbsolutePath());
                new Alert(Alert.AlertType.INFORMATION, "Daily report generated successfully!").show();
            }
        } catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate report").show();
        }
    }

    private void loadDashboardCounts() {
        int bookCount = booksService.getBookCount();
        int customerCount = customerService.getCustomerCount();
        int rentalCount = rentalService.getRentalCount();

        lblBookCount.setText(String.valueOf(bookCount));
        lblCustomerCount.setText(String.valueOf(customerCount));
        lblRentalCount.setText(String.valueOf(rentalCount));
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate today = LocalDate.now();
        lblDate.setText(today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // Set time (updates every second)
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime currentTime = LocalTime.now();
                    lblTime.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        loadDashboardCounts();
    }
}
