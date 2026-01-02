package accountManager;

import java.util.UUID;

import currencyManager.CurrencyType;

public abstract class Account {
    private final String accountNumber;
    private final String bankName;
    private Double balance;
    private Double debit;
    private final CurrencyType currencyType;
    private final AccountType accountType;

    public Account(String bankName, Double balance, CurrencyType currencyType, AccountType accountType) {
        this.accountNumber = generateId();
        this.bankName = bankName;
        this.balance = balance;
        this.debit = 0.0;
        this.currencyType = currencyType;
        this.accountType = accountType;
    }

    public String getAccountNum() {
        return accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public Double getBalance() {
        return balance;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public Double getDebit() {
        return debit;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void addAmount(Double amount) {
        this.balance += amount;
    }

    public void subAmount(Double amount) {
        this.balance -= amount;
    }

    public void addDebit(Double amount) {
        this.debit = amount;
    }

    private String generateId() {
        return "ACCO" + UUID.randomUUID();
    }
}
