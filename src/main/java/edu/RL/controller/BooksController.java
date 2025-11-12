package edu.RL.controller;

import com.google.protobuf.StringValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.Book;
import edu.RL.service.BooksService;
import edu.RL.service.BooksServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BooksController implements Initializable{

    BooksService booksService = new BooksServiceImpl();

    @FXML
    private JFXButton btnAddBook;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colAvailableCopies;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colISBN;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private Label lblbookId;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtAvailableCopies;

    @FXML
    private JFXTextField txtCategory;

    @FXML
    private JFXTextField txtISBN;

    @FXML
    private JFXTextField txtTitle;


    @FXML
    private TableView<Book> tableviewBooks;

    @FXML
    void btnAddBookOnAction(ActionEvent event) {
        booksService.addBook(new Book(
                lblbookId.getText(),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtCategory.getText(),
                txtISBN.getText(),
                Integer.parseInt(txtAvailableCopies.getText())
        ));
        loadItemTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colAvailableCopies.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));

//        tableviewBooks.getSelectionModel().selectedItemProperty().addListener((((observableValue, oldValue, newValue) -> {
//            if(null!=newValue){
//                setSelectedItem((Item) newValue);
//            }
        loadItemTable();
        try {
            setNextId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadItemTable(){
        tableviewBooks.setItems(booksService.getAll());
    }

    private void setNextId() throws SQLException {
        String nextId = booksService.generateNextBookId();
        lblbookId.setText(nextId);

    }
}
