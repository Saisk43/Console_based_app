import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class onlineShoppingService {
    private static onlineShoppingService instance;
    private final Map<String, User> users;
    private final Map<String, Product> products;
    private final Map<String, Order> orders;

    private onlineShoppingService() {
        orders = new ConcurrentHashMap<>();
        products = new ConcurrentHashMap<>();
        users = new ConcurrentHashMap<>();
    }

    public static synchronized onlineShoppingService getInstance() {
        if (instance == null) {
            instance = new onlineShoppingService();
        }
        return instance;
    }

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    // public void removeUser(String userId) {
    // users.remove(userId);
    // }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }

    public List<Product> searchProduct(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getCategory().toLowerCase().equals(keyword.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    public Order placeOrder(User user, ShoppingCart cart, Payment payment) {
        List<OrderItem> orderItem = new ArrayList<>();
        for (OrderItem item : cart.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            if (product.isAvailable(quantity)) {
                product.updateQuantity(quantity);
                orderItem.add(item);
            }
        }
        // if(orderItem == null)
        // System.out.println("No product in cart");
        // return;
        String orderId = generateOrderId();
        Order order = new Order(orderId, user, orderItem);
        orders.put(orderId, order);
        user.addOrder(order);
        cart.clear();
        double amount = order.getTotalAmount();
        if (payment.processPayment(amount)) {
            order.setStatus(OrderStatus.PROCESSING);
        } else {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();
                product.updateQuantity(quantity);
            }
            order.setStatus(OrderStatus.CANCELLED);
        }
        return order;

    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    private String generateOrderId() {
        return "ORDER" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
