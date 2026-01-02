import java.util.Map;

public class Cofee {

    private final String name;
    private final double price;
    private final Map<Ingrediance, Integer> ingrediance;

    public Cofee(String name, double price, Map<Ingrediance, Integer> ingrediance) {
        this.name = name;
        this.price = price;
        this.ingrediance = ingrediance;

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Map<Ingrediance, Integer> allIngrediances() {
        return ingrediance;
    }

    @Override
    public String toString() {
        return "Cofee [name=" + name + ", price=" + price + ", ingrediance=" + ingrediance.toString() + "]";
    }

}