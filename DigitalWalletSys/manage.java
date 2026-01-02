import currencyManager.CurrencyType;
import src.User;
import src.Wallet;
import src.WalletManager;

public class manage {
        public static void main(String[] args) {

                WalletManager walletManager = new WalletManager();

                User alice = walletManager.createWallet("Alice");
                User bob = walletManager.createWallet("Bob");

                System.out.println("Alice ID: " + alice.getId());
                System.out.println("Bob ID: " + bob.getId());

                walletManager.linkBankAccount(
                                alice.getId(),
                                "HDFC",
                                10000.0,
                                CurrencyType.INR, true);

                walletManager.linkCreditCard(
                                alice.getId(),
                                "ICICI",
                                5000.0,
                                CurrencyType.INR, true);

                walletManager.linkBankAccount(
                                bob.getId(),
                                "SBI",
                                2000.0,
                                CurrencyType.USD, false);

                Wallet aliceWallet = walletManager.getWallet(alice.getWalletId());
                Wallet bobWallet = walletManager.getWallet(bob.getWalletId());

                System.out.println("\n--- Sending Money ---");

                boolean success = walletManager.sendAmount(
                                alice.getId(),
                                bob.getId(),
                                1500.0,
                                CurrencyType.INR);

                System.out.println("Transaction Success: " + success);

                System.out.println("\n--- Balances After Transfer ---");

                aliceWallet.printAccounts();
                bobWallet.printAccounts();

                System.out.println("\n--- Alice Transactions ---");
                aliceWallet.printTransactions();

                System.out.println("\n--- Bob Transactions ---");
                bobWallet.printTransactions();
        }
}
