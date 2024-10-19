package org.jetbrains.finguard;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomePageController {

    @FXML
    private ComboBox<String> accountComboBox;

    @FXML
    private DatePicker transactionDate;

    @FXML
    private TextField transactionAmountField;

    @FXML
    private TextField transactionDescriptionField;

    @FXML
    private BarChart<String, Number> monthlyBarChart;

    @FXML
    private BarChart<String, Number> monthlySpendingChart;

    @FXML
    private TableView<Transaction> transactionTable;

    private final DatabaseHandler dbHandler = new DatabaseHandler(); // Mark dbHandler as final

    @FXML
    public void initialize() {
        // Initialize table columns
        initializeTable();

        // Load initial transaction data into the table and charts
        loadTransactionData();
    }

    @FXML
    public void handleUploadTransaction() {
        String selectedAccount = accountComboBox.getValue();
        String date = transactionDate.getValue().toString();
        double amount = Double.parseDouble(transactionAmountField.getText());
        String description = transactionDescriptionField.getText();

        // Assuming the account ID is stored in the ComboBox value
        int accountId = getAccountIdByName(selectedAccount);

        // Insert transaction into the database
        dbHandler.insertTransaction(accountId, date, amount, description);

        // Update charts and tables.
        loadTransactionData();
    }

    private int getAccountIdByName(String accountName) {
        // Add logic to retrieve the account ID based on account name from the database
        return 0; // Temporary placeholder
    }

    private void initializeTable() {
        TableColumn<Transaction, String> typeCol = new TableColumn<>("Transaction Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Transaction Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Transaction Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));

        transactionTable.getColumns().addAll(typeCol, dateCol, amountCol);
    }

    private void loadTransactionData() {
        // Clear existing data in the table and charts
        transactionTable.getItems().clear();
        monthlyBarChart.getData().clear();
        monthlySpendingChart.getData().clear();

        // Example data for TableView
        transactionTable.getItems().add(new Transaction("Deposit", "2024-10-14", 200.00));
        transactionTable.getItems().add(new Transaction("Withdrawal", "2024-10-15", 50.00));

        // Example data for BarChart
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        incomeSeries.getData().add(new XYChart.Data<>("2024-10-14", 200.00));
        incomeSeries.getData().add(new XYChart.Data<>("2024-10-15", 50.00));

        monthlyBarChart.getData().add(incomeSeries);

        // Example data for Spending Chart
        XYChart.Series<String, Number> spendingSeries = new XYChart.Series<>();
        spendingSeries.setName("Spending");
        spendingSeries.getData().add(new XYChart.Data<>("2024-10-14", 0));
        spendingSeries.getData().add(new XYChart.Data<>("2024-10-15", 50.00));

        monthlySpendingChart.getData().add(spendingSeries);
    }
}
