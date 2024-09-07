package java_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ATMSimulationGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private ATM atm;
    private JTextField amountField;
    private JPasswordField oldPinField;
    private JPasswordField newPinField;
    private JTextArea displayArea;
    private JButton balanceButton;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton changePinButton;
    private JButton transactionHistoryButton;

    public ATMSimulationGUI(ATM atm) {
        this.atm = atm;
        createView();
        setTitle("ATM Machine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createView() {
        JPanel panel = new JPanel(new GridBagLayout());
        getContentPane().add(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        panel.add(scrollPane, gbc);

        balanceButton = new JButton("Balance Inquiry");
        balanceButton.addActionListener(new BalanceButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(balanceButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Amount:"), gbc);

        amountField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(amountField, gbc);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new WithdrawButtonListener());
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(withdrawButton, gbc);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(new DepositButtonListener());
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel.add(depositButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Old PIN:"), gbc);

        oldPinField = new JPasswordField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(oldPinField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("New PIN:"), gbc);

        newPinField = new JPasswordField(10);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(newPinField, gbc);

        changePinButton = new JButton("Change PIN");
        changePinButton.addActionListener(new ChangePinButtonListener());
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(changePinButton, gbc);

        transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.addActionListener(new TransactionHistoryButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        panel.add(transactionHistoryButton, gbc);
    }

    private class BalanceButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            displayArea.setText("Your balance is: $" + atm.getBalance());
        }
    }

    private class WithdrawButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                atm.withdraw(amount);
                displayArea.setText("You have successfully withdrawn $" + amount);
            } catch (NumberFormatException ex) {
                displayArea.setText("Please enter a valid amount.");
            } catch (IllegalArgumentException ex) {
                displayArea.setText(ex.getMessage());
            }
        }
    }

    private class DepositButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                atm.deposit(amount);
                displayArea.setText("You have successfully deposited $" + amount);
            } catch (NumberFormatException ex) {
                displayArea.setText("Please enter a valid amount.");
            }
        }
    }

    private class ChangePinButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String oldPin = new String(oldPinField.getPassword());
                String newPin = new String(newPinField.getPassword());
                atm.changePin(oldPin, newPin);
                displayArea.setText("PIN successfully changed.");
            } catch (IllegalArgumentException ex) {
                displayArea.setText(ex.getMessage());
            }
        }
    }

    private class TransactionHistoryButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> transactionHistory = atm.getTransactionHistory();
            if (transactionHistory.isEmpty()) {
                displayArea.setText("No transactions yet.");
            } else {
                StringBuilder history = new StringBuilder("Transaction History:\n");
                for (String transaction : transactionHistory) {
                    history.append(transaction).append("\n");
                }
                displayArea.setText(history.toString());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATM atm = new ATM(1000.0, "1234");
            new ATMSimulationGUI(atm).setVisible(true);
        });
    }
}