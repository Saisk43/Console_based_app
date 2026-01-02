package accountManager;

import currencyManager.CurrencyType;

public interface PaymentMethods {
    boolean pay(Double amount);

    void receive(double amount, CurrencyType currencyType);

    String getAccountNumber();
}
