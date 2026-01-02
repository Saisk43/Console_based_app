package currencyManager;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    private static CurrencyConverter instance;
    private Map<CurrencyType, Double> exchangeRate;

    public CurrencyConverter() {
        exchangeRate = new HashMap<>();
        initializeExchangeRate();
    }

    public static CurrencyConverter getInstance() {
        if (instance == null)
            instance = new CurrencyConverter();
        return instance;
    }

    public void initializeExchangeRate() {
        exchangeRate.put(CurrencyType.INR, 1.0);
        exchangeRate.put(CurrencyType.USD, 90.0);
        exchangeRate.put(CurrencyType.EUR, 95.0);
    }

    public Double convertCurrency(double amount, CurrencyType sendCurrencyType, CurrencyType receiveCurrencyType) {
        return (amount * exchangeRate.get(sendCurrencyType)) / exchangeRate.get(receiveCurrencyType);
    }
}
