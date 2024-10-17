package org.jetbrains.finguard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    // Method to create the 'accounts' table if it doesn't exist
    public void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS accounts (\n"
                + "    id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    name text NOT NULL,\n"
                + "    balance real\n"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
