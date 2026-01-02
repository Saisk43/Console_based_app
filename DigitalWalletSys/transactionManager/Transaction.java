package transactionManager;

import java.util.UUID;

import currencyManager.CurrencyType;

public class Transaction {
    private final String id;
    private final String senderWalletId;
    private final String receiverWalletId;
    private final double amount;
    private final TransactionType type;
    private final TransactionStatus status;
    private final CurrencyType currency;

    public Transaction(String senderWalletId, String receiverWalletId, double amount, TransactionType type,
            TransactionStatus status, CurrencyType currency) {
        this.id = generateTransactionId();
        this.senderWalletId = senderWalletId;
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public String getSenderWalletId() {
        return senderWalletId;
    }

    public String getReceiverWalletId() {
        return receiverWalletId;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", senderWalletId=" + senderWalletId + ", receiverWalletId=" + receiverWalletId
                + ", amount=" + amount + ", type=" + type + ", status=" + status + ", currency=" + currency + "]";
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    private String generateTransactionId() {
        return "TSI" + UUID.randomUUID();
    }
}
