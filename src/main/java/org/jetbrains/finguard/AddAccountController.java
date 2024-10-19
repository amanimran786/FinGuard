package org.jetbrains.finguard;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAccountController {

    @FXML
    private TextField accountNameField;

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private TextField currentBalanceField;

    @FXML
    private DatePicker openingDatePicker;

    @FXML
    private TableView<Account> accountTable;

    private final DatabaseHandler dbHandler = new DatabaseHandler(); // Mark dbHandler as final

    @FXML
    public void initialize() {
        dbHandler.createTables(); // Ensure tables are created when the controller is initialized
        initializeComboBox();
        initializeTable();
        loadAccounts(); // Load accounts when the UI is initialized
    }

    private void initializeComboBox() {
        accountTypeComboBox.setItems(FXCollections.observableArrayList("Checking", "Savings", "Investment"));
    }

    @FXML
    public void handleSaveAccount() {
        String accountName = accountNameField.getText();
        String accountType = accountTypeComboBox.getValue();
        double balance = Double.parseDouble(currentBalanceField.getText());
        String openingDate = openingDatePicker.getValue().toString();

        // Insert the account into the database
        dbHandler.insertAccount(accountName, accountType, balance, openingDate);

        // Refresh the account table with the newly added account
        loadAccounts();

        // Clear the input fields
        accountNameField.clear();
        currentBalanceField.clear();
        openingDatePicker.setValue(null);
        accountTypeComboBox.setValue(null);

        // After saving, navigate to the home page
        goToHomePage();
    }

    private void goToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jetbrains/finguard/home-page.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) accountNameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initializeTable() {
        // Initialize the account table columns
        TableColumn<Account, String> nameCol = new TableColumn<>("Account Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("accountName"));

        TableColumn<Account, String> typeCol = new TableColumn<>("Account Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("accountType"));

        TableColumn<Account, Double> balanceCol = new TableColumn<>("Current Balance");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));

        TableColumn<Account, String> dateCol = new TableColumn<>("Opening Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("openingDate"));

        accountTable.getColumns().addAll(nameCol, typeCol, balanceCol, dateCol);
    }

    private void loadAccounts() {
        // Clear the table
        accountTable.getItems().clear();

        // Load accounts from the database
        accountTable.getItems().addAll(dbHandler.getAllAccounts());
    }
}
