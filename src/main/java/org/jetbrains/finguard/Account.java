package org.jetbrains.finguard;

public class Account {
    private String accountName;
    private String accountType;
    private double balance;
    private String openingDate;

    public Account(String accountName, String accountType, double balance, String openingDate) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.openingDate = openingDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getOpeningDate() {
        return openingDate;
    }
}
