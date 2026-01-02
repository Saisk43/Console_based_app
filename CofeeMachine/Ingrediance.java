public class Ingrediance {
    private final String name;
    private int quantity;

    public Ingrediance(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;

    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }

}
