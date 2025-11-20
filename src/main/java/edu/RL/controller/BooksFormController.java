package edu.RL.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.RL.dto.Book;
import edu.RL.service.Service.BooksService;
import edu.RL.service.BooksServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BooksFormController implements Initializable{

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
    private JFXTextField txtBookId;

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
                txtBookId.getText(),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtCategory.getText(),
                txtISBN.getText(),
                Integer.parseInt(txtAvailableCopies.getText())
        ));
        loadBookTable();
    }

    @FXML
    void btnDeleteBookOnAction(ActionEvent event) {
        booksService.deleteBook(txtBookId.getText());
        loadBookTable();
    }

    @FXML
    void btnSearchBookOnAction(ActionEvent event) {
        Book searchBook = booksService.searchBook(txtBookId.getText(), txtTitle.getText());
        txtTitle.setText(searchBook.getTitle());
        txtAuthor.setText(searchBook.getAuthor());
        txtCategory.setText(searchBook.getCategory());
        txtISBN.setText(searchBook.getIsbn());
        txtAvailableCopies.setText(searchBook.getAvailableCopies().toString());
    }

    @FXML
    void btnUpdateBookOnAction(ActionEvent event) {
        booksService.updateBook(new Book(
                txtBookId.getText(),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtCategory.getText(),
                txtISBN.getText(),
                Integer.parseInt(txtAvailableCopies.getText())
        ));
        loadBookTable();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colAvailableCopies.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));

        tableviewBooks.getSelectionModel().selectedItemProperty().addListener((((observableValue, oldValue, newValue) -> {
            if(null!=newValue){
                setSelectedBook((Book) newValue);
            }
        })));

        loadBookTable();

        try {
            setNextId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSelectedBook(Book book) {
        txtBookId.setText(book.getBookId());
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        txtCategory.setText(book.getCategory());
        txtISBN.setText(book.getIsbn());
        txtAvailableCopies.setText(String.valueOf(txtAvailableCopies.getText()));
    }

    private void loadBookTable(){
        tableviewBooks.setItems(booksService.getAll());
    }

    private void setNextId() throws SQLException {
        String nextId = booksService.generateNextBookId();
        txtBookId.setText(nextId);

    }
}
