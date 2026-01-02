package accountManager;

import currencyManager.CurrencyConverter;
import currencyManager.CurrencyType;

public class CreditCard extends Account implements PaymentMethods {
    private Double remainingLimit;

    public CreditCard(String bankName, Double balance, CurrencyType type, AccountType accountType) {
        super(bankName, balance, type, accountType);
        this.remainingLimit = 5000.0;

    }

    private Double getRemainingLimit() {
        return remainingLimit;
    }

    private void updateRemainingLimit(Double amount) {
        remainingLimit -= amount;
    }

    @Override
    public boolean pay(Double amount) {
        if (amount <= getRemainingLimit()) {
            updateRemainingLimit(amount);
            addDebit(amount);
        } else {
            System.out.println("Credit Card limit reached");
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
        return (" | CreditCard : " + getBankName()
                + " | CardNo: " + getAccountNumber()
                + " | Debet: " + getDebit()
                + " | Remaining Limit: " + getRemainingLimit());
    }

}
