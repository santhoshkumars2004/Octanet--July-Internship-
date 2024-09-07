package java_project;
import java.util.ArrayList;

public class ATM {
    private double balance;
    private String pin;
    private ArrayList<String> transactionHistory;

    public ATM(double initialBalance, String initialPin) {
        this.balance = initialBalance;
        this.pin = initialPin;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawal: $" + amount);
        } else {
            throw new IllegalArgumentException("Insufficient balance!");
        }
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposit: $" + amount);
    }

    public void changePin(String oldPin, String newPin) {
        if (this.pin.equals(oldPin)) {
            this.pin = newPin;
        } else {
            throw new IllegalArgumentException("Incorrect old PIN!");
        }
    }

    public boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }
}