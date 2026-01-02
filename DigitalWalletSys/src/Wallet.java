package src;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import accountManager.PaymentMethods;
import currencyManager.CurrencyType;
import transactionManager.Transaction;

public class Wallet {
    private final String id;
    private final List<PaymentMethods> paymentMethods;
    private PaymentMethods primary;
    private List<Transaction> transactions;

    public Wallet() {
        this.id = generateWalletId();
        this.paymentMethods = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public boolean setPrimary(String accountNumber) {
        for (PaymentMethods method : paymentMethods) {
            if (method.getAccountNumber() == accountNumber) {
                this.primary = method;
                return true;
            }
        }
        System.out.println(accountNumber + "not linked");
        return false;
    }

    public PaymentMethods getPrimary() {
        return primary;
    }

    public List<PaymentMethods> getPaymentMethods() {
        return this.paymentMethods;
    }

    public void printAccounts() {
        System.out.println("Accounts in Wallet " + id);

        for (PaymentMethods method : paymentMethods) {
            System.out.println(method.toString());
        }
    }

    public void printTransactions() {
        for (Transaction trans : transactions) {
            System.out.println(trans.toString());
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addPaymentMethods(PaymentMethods account) {
        this.paymentMethods.add(account);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    private String generateWalletId() {
        return "DWS" + UUID.randomUUID();
    }

    public boolean debitAmount(Double amount) {

        if (primary == null) {
            System.out.println("Payment method is not linked");
            return false;
        }

        return primary.pay(amount);

    }

    public boolean creditAmount(Double amount, CurrencyType currencyType) {

        if (primary == null) {
            System.out.println("Payment Method is not linked");
            return false;
        }
        primary.receive(amount, currencyType);
        return true;
    }

}
