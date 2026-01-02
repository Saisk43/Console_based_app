import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private Map<String, OrderItem> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public void addItem(onlineShoppingService shop, String productId, int quantity) {
        Product product = null;
        if (items.containsKey(productId)) {
            product = items.get(productId).getProduct();
            quantity += items.get(productId).getQuantity();
        } else {
            product = shop.getProduct(productId);
        }
        items.put(productId, new OrderItem(product, quantity));
    }

    public void updateQuantity(String productId, int quantity) {
        if (items.containsKey(productId)) {
            OrderItem order = items.get(productId);
            items.put(productId, new OrderItem(order.getProduct(), quantity));
        }
    }

    public void removeItem(String productId) {
        items.remove(productId);
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public void clear() {
        items.clear();
    }

}
