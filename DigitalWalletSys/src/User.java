package src;

import java.util.UUID;

public class User {
    private final String id;
    private final String name;
    private final String walletId;

    public User(String name, String walletId) {
        this.id = generateUserId(name);
        this.name = name;
        this.walletId = walletId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWalletId() {
        return walletId;
    }

    private String generateUserId(String name) {
        return name + UUID.randomUUID();
    }
}
