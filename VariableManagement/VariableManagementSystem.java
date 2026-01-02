// Import necessary Java utility classes for collections and scanning user input
import java.util.*;

// Main class that implements a variable management system with transaction support
public class VariableManagementSystem {
    // Inner class to store the state of variables and value counts during a transaction
    private static class TransactionState {
        Map<String, Integer> variables;    // Map to store variable names and their values
        Map<Integer, Integer> valueCount;  // Map to track how many variables have each value

        // Default constructor for creating a new empty transaction state
        TransactionState() {
            variables = new HashMap<>();     // Initialize empty variables map
            valueCount = new HashMap<>();    // Initialize empty value count map
        }

        // Constructor to create a copy of the current state for transaction tracking
        TransactionState(Map<String, Integer> vars, Map<Integer, Integer> counts) {
            variables = new HashMap<>(vars);     // Create a deep copy of variables map
            valueCount = new HashMap<>(counts);  // Create a deep copy of value count map
        }
    }

    // Stack to track transaction states for rollback functionality
    private Stack<TransactionState> transactions;
    // Map to store the current variable names and their values
    private Map<String, Integer> variables;
    // Map to track how many variables currently have each value
    private Map<Integer, Integer> valueCount;

    // Constructor to initialize the system with empty collections
    public VariableManagementSystem() {
        transactions = new Stack<>();    // Initialize transaction stack
        variables = new HashMap<>();     // Initialize variables map
        valueCount = new HashMap<>();    // Initialize value count map
    }

    // Set a variable to a specific value
    public void set(String var, int value) {
        // If the variable already exists, decrement the count of its old value
        if (variables.containsKey(var)) {
            int oldValue = variables.get(var);                           // Get the old value
            valueCount.put(oldValue, valueCount.get(oldValue) - 1);      // Decrement count of old value
            if (valueCount.get(oldValue) == 0) {                         // If count reaches zero
                valueCount.remove(oldValue);                             // Remove the value from the count map
            }
        }

        // Set the new value for the variable
        variables.put(var, value);                                      // Update or add the variable with new value
        valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);   // Increment the count for this value
    }

    // Get the value of a variable (returns null if variable doesn't exist)
    public Integer get(String var) {
        return variables.getOrDefault(var, null);   // Return the value of the variable or null if not found
    }

    // Remove a variable from the system
    public void unset(String var) {
        if (variables.containsKey(var)) {                               // Check if the variable exists
            int value = variables.get(var);                             // Get the value of the variable
            valueCount.put(value, valueCount.get(value) - 1);           // Decrement the count for this value
            if (valueCount.get(value) == 0) {                           // If count reaches zero
                valueCount.remove(value);                               // Remove the value from the count map
            }
            variables.remove(var);                                      // Remove the variable
        }
    }

    // Count the number of variables that have a specific value
    public Integer count(int value) {
        return valueCount.getOrDefault(value, 0);   // Return the count for this value or 0 if not found
    }

    // Begin a new transaction by saving the current state
    public void begin() {
        transactions.push(new TransactionState(variables, valueCount));  // Save current state to the transaction stack
    }

    // Roll back to the previous transaction state
    public boolean rollback() {
        if (transactions.isEmpty()) {            // Check if there are any transactions to roll back
            return false;                        // Return false if no transactions exist
        }
        TransactionState state = transactions.pop();  // Get the previous state from the stack
        variables = state.variables;                  // Restore variables from the previous state
        valueCount = state.valueCount;                // Restore value counts from the previous state
        return true;                                  // Return true to indicate successful rollback
    }

    // Commit all transactions (clear the transaction stack)
    public boolean commit() {
        if (transactions.isEmpty()) {           // Check if there are any transactions to commit
            return false;                       // Return false if no transactions exist
        }
        transactions.clear();                   // Clear all transactions (they are now permanent)
        return true;                            // Return true to indicate successful commit
    }

    // Main method to handle user input and program execution
    public static void main(String[] args) {
        VariableManagementSystem system = new VariableManagementSystem();  // Create a new system instance
        Scanner scanner = new Scanner(System.in);                          // Create scanner for user input

        System.out.println("Enter commands (type EXIT to quit):");         // Print initial instructions
        while (true) {                                                     // Continuous loop for command processing
            String line = scanner.nextLine().trim();                       // Read and trim user input
            if (line.equalsIgnoreCase("EXIT")) {                           // Check for exit command
                break;                                                     // Exit the loop if EXIT command is given
            }

            String[] parts = line.split("\\s+");         // Split input by whitespace  // Results in ["set", "x", "10"]
            String command = parts[0].toUpperCase();           // Extract and convert command to uppercase  // Results in "SET"

            try {
                switch (command) {                                         // Process different commands
                    case "SET":                                            // SET command handling
                        if (parts.length != 3) {                           // Validate command format
                            System.out.println("Invalid SET command. Usage: SET variable value");
                            continue;                                      // Skip to next iteration if invalid
                        }
                        system.set(parts[1], Integer.parseInt(parts[2]));  // Set the variable to the given value
                        break;

                    case "GET":                                            // GET command handling
                        if (parts.length != 2) {                           // Validate command format
                            System.out.println("Invalid GET command. Usage: GET variable");
                            continue;                                      // Skip to next iteration if invalid
                        }
                        Integer value = system.get(parts[1]);              // Get the variable value
                        System.out.println(value != null ? value : "null"); // Print value or "null" if not found
                        break;

                    case "UNSET":                                          // UNSET command handling
                        if (parts.length != 2) {                           // Validate command format
                            System.out.println("Invalid UNSET command. Usage: UNSET variable");
                            continue;                                      // Skip to next iteration if invalid
                        }
                        system.unset(parts[1]);                            // Unset (remove) the variable
                        break;

                    case "COUNT":                                          // COUNT command handling
                        if (parts.length != 2) {                           // Validate command format
                            System.out.println("Invalid COUNT command. Usage: COUNT value");
                            continue;                                      // Skip to next iteration if invalid
                        }
                        System.out.println(system.count(Integer.parseInt(parts[1]))); // Print count of variables with given value
                        break;

                    case "BEGIN":                                          // BEGIN command handling
                        system.begin();                                    // Begin a new transaction
                        break;

                    case "ROLLBACK":                                       // ROLLBACK command handling
                        if (!system.rollback()) {                          // Try to roll back and check if successful
                            System.out.println("NO TRANSACTION");          // Print error if no transaction exists
                        }
                        break;

                    case "COMMIT":                                         // COMMIT command handling
                        if (!system.commit()) {                            // Try to commit and check if successful
                            System.out.println("NO TRANSACTION");          // Print error if no transaction exists
                        }
                        break;

                    default:                                               // Handle unknown commands
                        System.out.println("Unknown command: " + command); // Print error for unknown command
                }
            } catch (NumberFormatException e) {                            // Handle number format exceptions
                System.out.println("Invalid number format");               // Print error for invalid number format
            } catch (Exception e) {                                        // Handle any other exceptions
                System.out.println("Error: " + e.getMessage());            // Print error message
            }
        }
        scanner.close();                                                   // Close the scanner to prevent resource leak
    }
}
