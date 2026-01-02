import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        onlineShoppingService shop = onlineShoppingService.getInstance();
        Product product1 = new Product("P001", "Poco", "Smartphone", "High-end smartphone", 999.99, 10);
        Product product2 = new Product("P002", "Asus", "Laptop", "Powerful gaming laptop", 1999.99, 5);
        shop.addProduct(product1);
        shop.addProduct(product2);
        Scanner scanner = new Scanner(System.in);
        User user = null;
        ShoppingCart cart = null;
        boolean run = true;
        while (run) {
            System.out.println(
                    "\n1 - register user\n2 - Search product\n3 - add product to Cart\n4 - place order\n5 - Exit");

            String input = scanner.next();
            switch (input) {
                case "1":
                    System.out.println("Enter your id ");
                    String id = scanner.next();
                    System.out.println("Enter your name ");
                    String name = scanner.next();
                    System.out.println("Enter your email ");
                    String email = scanner.next();
                    System.out.println("Enter you password ");
                    String password = scanner.next();
                    user = new User(id, name, email, password);
                    shop.registerUser(user);
                    System.out.println("User created");
                    break;
                case "2":
                    System.out.println("Enter category to search");
                    String keyword = scanner.next();
                    List<Product> allProduct = shop.searchProduct(keyword);
                    for (Product product : allProduct) {
                        System.out.println(product.toString());
                    }
                    break;
                case "3":
                    System.out.println("Enter product id to add to cart ");
                    String productId = scanner.next();
                    System.out.println("Enter product quantity ");
                    String quantity = scanner.next();
                    cart = new ShoppingCart();
                    cart.addItem(shop, productId, Integer.parseInt(quantity));
                    System.out.println(cart.getItems());

                    break;
                case "4":
                    Payment Payment = new Payment();
                    System.out.println("confirm payment");
                    String payMode = scanner.next();
                    try {
                        shop.placeOrder(user, cart, Payment);
                        System.out.println("Order successful");
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("user or cart is empty");
                    }

                    break;
                case "5":
                    run = false;
                    scanner.close();
                    System.out.println("Exiting..");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }

        // onlineShoppingService shoppingService = onlineShoppingService.getInstance();

        // // Register users
        // User user1 = new User("U001", "John Doe", "john@example.com", "password123");
        // User user2 = new User("U002", "Jane Smith", "jane@example.com",
        // "password456");
        // shoppingService.registerUser(user1);
        // shoppingService.registerUser(user2);

        // // Add products
        // Product product1 = new Product("P001", "Poco", "Smartphone", "High-end
        // smartphone", 999.99, 10);
        // Product product2 = new Product("P002", "Asus", "Laptop", "Powerful gaming
        // laptop", 1999.99, 5);
        // shoppingService.addProduct(product1);
        // shoppingService.addProduct(product2);

        // // User 1 adds products to cart and places an order
        // ShoppingCart cart1 = new ShoppingCart();
        // cart1.addItem(product1, 2);
        // cart1.addItem(product2, 1);
        // Payment payment1 = new Payment();
        // Order order1 = shoppingService.placeOrder(user1, cart1, payment1);
        // System.out.println("Order placed: " + order1.getId());

        // // User 2 searches for products and adds to cart
        // List<Product> searchResults = shoppingService.searchProduct("laptop");
        // System.out.println("Search Results:");
        // for (Product product : searchResults) {
        // System.out.println(product.getName());
        // }

        // ShoppingCart cart2 = new ShoppingCart();
        // cart2.addItem(searchResults.get(0), 1);
        // Payment payment2 = new Payment();
        // Order order2 = shoppingService.placeOrder(user2, cart2, payment2);
        // System.out.println("Order placed: " + order2.getId());

        // // User 1 views order history
        // List<Order> userOrders = user1.getOrders();
        // System.out.println("User 1 Order History:");
        // for (Order order : userOrders) {
        // System.out.println("Order ID: " + order.getId());
        // System.out.println("Total Amount: $" + order.getTotalAmount());
        // System.out.println("Status: " + order.getStatus());
        // };

    }
}
