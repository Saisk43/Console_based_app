package accountManager;

import currencyManager.CurrencyConverter;
import currencyManager.CurrencyType;

public class BankAccount extends Account implements PaymentMethods {

    public BankAccount(String bankName, Double balance, CurrencyType type, AccountType accountType) {
        super(bankName, balance, type, accountType);
    }

    @Override
    public boolean pay(Double amount) {
        if (amount <= getBalance()) {
            subAmount(amount);
        } else {
            System.out.println("Insuffient balance");
            return false;
        }
        return true;
    }

    @Override
    public String getAccountNumber() {
        return getAccountNum();
    }

    @Override
    public void receive(double amount, CurrencyType currencyType) {
        if (this.getCurrencyType() != currencyType)
            amount = CurrencyConverter.getInstance().convertCurrency(amount, currencyType, this.getCurrencyType());
        addAmount(amount);

    }

    @Override
    public String toString() {
        return (" | Bank: " + getBankName()
                + " | AccNo: " + getAccountNumber()
                + " | Balance: " + getBalance()
                + " | CurrencyType: " + getCurrencyType());
    }

}
