package org.jetbrains.finguard;

import java.sql.*;

public class DatabaseHandler {

    // SQLite database URL pointing to the finGuard.db file in the root directory
    private static final String DATABASE_URL = "jdbc:sqlite:./finGuard.db";

    public Connection connect() {
        Connection conn = null;
        try {
            // Explicitly load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Connect to the database
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return conn;
    }

    // Method to create accounts and transactions tables
    public void createTables() {
        String createAccountsTable = "CREATE TABLE IF NOT EXISTS accounts ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "type TEXT NOT NULL, "
                + "balance REAL NOT NULL, "
                + "opening_date TEXT NOT NULL"
                + ");";

        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "account_id INTEGER NOT NULL, "
                + "transaction_date TEXT NOT NULL, "
                + "transaction_amount REAL NOT NULL, "
                + "description TEXT, "
                + "FOREIGN KEY(account_id) REFERENCES accounts(id)"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // Create accounts and transactions tables
            stmt.execute(createAccountsTable);
            stmt.execute(createTransactionsTable);
            System.out.println("Tables created or already exist.");

            // Insert sample data if no data exists
            preloadData();

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    // Method to preload sample data if tables are empty
    private void preloadData() {
        // Check if accounts table is empty
        String checkAccountsSql = "SELECT COUNT(*) FROM accounts";
        String insertAccountSql = "INSERT INTO accounts(name, type, balance, opening_date) VALUES(?, ?, ?, ?)";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkAccountsSql)) {

            if (rs.next() && rs.getInt(1) == 0) {  // If the accounts table is empty
                try (PreparedStatement pstmt = conn.prepareStatement(insertAccountSql)) {
                    // Insert sample accounts
                    pstmt.setString(1, "Sample Account 1");
                    pstmt.setString(2, "Checking");
                    pstmt.setDouble(3, 1000.0);
                    pstmt.setString(4, "2024-01-01");
                    pstmt.executeUpdate();

                    pstmt.setString(1, "Sample Account 2");
                    pstmt.setString(2, "Savings");
                    pstmt.setDouble(3, 2500.0);
                    pstmt.setString(4, "2024-02-15");
                    pstmt.executeUpdate();

                    System.out.println("Sample accounts inserted.");
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        // Check if transactions table is empty and preload transactions if necessary
        String checkTransactionsSql = "SELECT COUNT(*) FROM transactions";
        String insertTransactionSql = "INSERT INTO transactions(account_id, transaction_date, transaction_amount, description) VALUES(?, ?, ?, ?)";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkTransactionsSql)) {

            if (rs.next() && rs.getInt(1) == 0) {  // If the transactions table is empty
                try (PreparedStatement pstmt = conn.prepareStatement(insertTransactionSql)) {
                    // Insert sample transactions for account 1
                    pstmt.setInt(1, 1); // Assuming account with ID 1 exists
                    pstmt.setString(2, "2024-01-10");
                    pstmt.setDouble(3, 500.0);
                    pstmt.setString(4, "Deposit");
                    pstmt.executeUpdate();

                    pstmt.setInt(1, 1);
                    pstmt.setString(2, "2024-01-20");
                    pstmt.setDouble(3, -100.0);
                    pstmt.setString(4, "Withdrawal");
                    pstmt.executeUpdate();

                    // Insert sample transactions for account 2
                    pstmt.setInt(1, 2); // Assuming account with ID 2 exists
                    pstmt.setString(2, "2024-02-18");
                    pstmt.setDouble(3, 1000.0);
                    pstmt.setString(4, "Deposit");
                    pstmt.executeUpdate();

                    System.out.println("Sample transactions inserted.");
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void insertTransaction(int accountId, String date, double amount, String description) {

    }

    public void insertAccount(String accountName, String accountType, double balance, String openingDate) {
    }

    public Account getAllAccounts() {
        return null;
    }

    // Other database methods...
}
