package src;

import java.util.HashMap;
import java.util.Map;

import accountManager.AccountType;
import accountManager.BankAccount;
import accountManager.CreditCard;
import accountManager.PaymentMethods;
import currencyManager.CurrencyType;
import transactionManager.Transaction;
import transactionManager.TransactionStatus;
import transactionManager.TransactionType;

public class WalletManager {
    private Map<String, User> users;
    private Map<String, Wallet> wallets;

    public WalletManager() {
        users = new HashMap<>();
        wallets = new HashMap<>();
    }

    public User createWallet(String name) {
        Wallet wallet = new Wallet();
        User user = new User(name, wallet.getId());
        users.put(user.getId(), user);
        wallets.put(wallet.getId(), wallet);
        return user;
    }

    public Map<String, User> getAllUsers() {
        return users;
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public Wallet getWallet(String id) {
        return wallets.get(id);
    }

    public void linkBankAccount(String userId, String bankName, Double balance, CurrencyType currencyType,
            boolean primary) {
        Wallet wallet = wallets.get(users.get(userId).getWalletId());
        AccountType accountType = AccountType.BankAccount;
        PaymentMethods bankAccount = new BankAccount(bankName, balance, currencyType, accountType);
        wallet.addPaymentMethods(bankAccount);
        if (wallet.getPrimary() == null || primary == true)
            wallet.setPrimary(bankAccount.getAccountNumber());
    }

    public void linkCreditCard(String userId, String bankName, Double balance, CurrencyType currencyType,
            boolean primary) {
        Wallet wallet = wallets.get(users.get(userId).getWalletId());
        AccountType accountType = AccountType.CREDIT_CARD;
        PaymentMethods creditCard = new CreditCard(bankName, balance, currencyType, accountType);
        wallet.addPaymentMethods(creditCard);
        if (wallet.getPrimary() == null || primary == true)
            wallet.setPrimary(creditCard.getAccountNumber());
    }

    public boolean sendAmount(String senderId, String receiverId, double amount, CurrencyType currencyType) {
        User sender = users.get(senderId);
        User receiver = users.get(receiverId);
        if (sender == null || receiver == null) {
            System.out.println("Invalid ID, try again");
            return false;
        }

        Wallet senderWallet = wallets.get(sender.getWalletId());
        Wallet receiverWallet = wallets.get(receiver.getWalletId());
        // check sender receiver has enabled payment method
        if (senderWallet.getPaymentMethods().size() == 0 || receiverWallet.getPaymentMethods().size() == 0) {
            System.out.println("sender or receiver has no account");
            return false;
        }
        TransactionStatus status = TransactionStatus.CANCELED;
        // if can debit
        if (senderWallet.debitAmount(amount)) {
            // credit
            if (receiverWallet.creditAmount(amount, currencyType))
                status = TransactionStatus.SUCCESSFULL;
            else
                senderWallet.creditAmount(amount, currencyType);
        }
        // transaction history
        senderWallet.addTransaction(new Transaction(senderId, receiverId, amount, TransactionType.DEBEIT,
                status, currencyType));
        receiverWallet.addTransaction(new Transaction(senderId, receiverId, amount, TransactionType.CREDIT,
                status, currencyType));
        return true;
    }

}
