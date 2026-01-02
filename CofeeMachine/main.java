import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        VendingMachine machine = VendingMachine.getInstance();
        Scanner scaner = new Scanner(System.in);
        while (true) {
            System.out.println("1 - Search menu\n2 - Order cofee\n3 - Exit");
            String choise = scaner.next();
            switch (choise) {
                case "1":
                    machine.printMenu();
                    // System.out.println("
                    // ");
                    break;
                case "2":
                    System.out.println("Enter Cofee name please");
                    String name = scaner.next();
                    System.out.println("Pay Amount");
                    Double amount = scaner.nextDouble();
                    if (machine.orderCofee(name, amount))
                        System.out.println("Enjoy you cofee");
                    break;
                case "3":
                    System.out.println("Exiting...");
                    scaner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}
