import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    private static VendingMachine instance;
    private HashMap<String, Ingrediance> allIngrediance;
    private HashMap<String, Cofee> CofeeMenu;

    public VendingMachine() {
        this.allIngrediance = new HashMap<>();
        this.CofeeMenu = new HashMap<>();
        initializeIngrediance();
        initializeCofeeMenu();
    }

    public static VendingMachine getInstance() {
        if (instance == null) {
            instance = new VendingMachine();
        }
        return instance;
    }

    private void initializeIngrediance() {
        addIngrediance(new Ingrediance("water", 20));
        addIngrediance(new Ingrediance("sugar", 10));
        addIngrediance(new Ingrediance("cofee powder", 10));
        addIngrediance(new Ingrediance("milk", 20));
        System.out.println(allIngrediance);
    }

    private void initializeCofeeMenu() {
        Map<Ingrediance, Integer> espressoIngrediance = new HashMap<>();
        espressoIngrediance.put(allIngrediance.get("water"), 1);
        espressoIngrediance.put(allIngrediance.get("cofee powder"), 1);
        addCofee(new Cofee("Espresso", 2.5, espressoIngrediance));

        Map<Ingrediance, Integer> cappuccinoIngrediance = new HashMap<>();
        cappuccinoIngrediance.put(allIngrediance.get("water"), 1);
        cappuccinoIngrediance.put(allIngrediance.get("cofee powder"), 1);
        cappuccinoIngrediance.put(allIngrediance.get("milk"), 1);
        addCofee(new Cofee("Cappuccino", 3.5, cappuccinoIngrediance));
    }

    public void addIngrediance(Ingrediance ingrediance) {
        allIngrediance.put(ingrediance.getName(), ingrediance);
    }

    public void addCofee(Cofee cofee) {
        CofeeMenu.put(cofee.getName(), cofee);
    }

    public List<String> printMenu() {
        List<String> items = new ArrayList<>();
        for (Cofee item : CofeeMenu.values()) {
            System.out.println(item.toString());
        }
        return items;

    }

    public Cofee selectCofee(String name) {

        return CofeeMenu.get(name);
    }

    public boolean orderCofee(String name, double amount) {
        Cofee cofee = selectCofee(name);
        if (cofee != null && amount >= cofee.getPrice()) {
            if (checkIngerdiance(cofee.allIngrediances())) {
                updateIngrediance(cofee.allIngrediances());
                double change = amount - cofee.getPrice();
                if (change > 0) {
                    System.out.println("Please collect your change: " + change);
                }
                return true;
            } else {
                System.out.println("Insufficient Ingrediance for " + cofee.getName());
            }
        } else {
            System.out.println("Insufficient balance or cofee not available for " + cofee.getName());
        }
        return false;
    }

    private boolean checkIngerdiance(Map<Ingrediance, Integer> recipe) {
        for (Map.Entry<Ingrediance, Integer> item : recipe.entrySet()) {
            int resource = item.getKey().getQuantity();
            if (resource < item.getValue()) {
                return false;
            }
        }
        return true;
    }

    private void updateIngrediance(Map<Ingrediance, Integer> recipe) {
        for (Map.Entry<Ingrediance, Integer> item : recipe.entrySet()) {
            item.getKey().updateQuantity(-item.getValue());

            if (item.getKey().getQuantity() < 3) {
                System.out.println("Low inventory alert: " + item.getKey().getName());
            }
        }

    }
}
