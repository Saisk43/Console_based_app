import java.util.List;

public class Order {
    private final String id;
    private final User user;
    private final List<OrderItem> items;
    private final double totalAmount;
    private OrderStatus status;

    public Order(String id, User user, List<OrderItem> items) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.totalAmount = calculateAmount();
        this.status = OrderStatus.PENDING;
    }

    private double calculateAmount() {
        double total = 0;
        for (OrderItem item : items) {
            double price = item.getProduct().getPrice();
            int quantity = item.getQuantity();
            total += price * quantity;
        }
        return total;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
