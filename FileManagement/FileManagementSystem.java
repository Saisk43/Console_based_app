import java.util.*; // Import required Java utilities

/**
 * FileNode class represents a node in the file system tree structure.
 * Each node can be either a file or a directory and contains relevant
 * information.
 */
class FileNode {
    private String name; // Stores name of file/directory (e.g., "documents")
    private boolean isDirectory; // true for directory, false for file
    private String content; // Stores file content (empty for directories)
    private Map<String, FileNode> children; // Stores child nodes (key: name, value: node)
    private FileNode parent; // Reference to parent node (null for root)
    private boolean isDeleted; // Tracks if node is in trash

    public FileNode(String name, boolean isDirectory, FileNode parent) { // Constructor to create new file/directory
        this.name = name; // Set the name (e.g., "report.txt")
        this.isDirectory = isDirectory; // Set if it's directory or file
        this.content = ""; // Initialize empty content
        this.children = new HashMap<>(); // Initialize empty children map
        this.parent = parent; // Set parent reference
        this.isDeleted = false; // Initially not deleted
    }

    // Getters and setters
    public String getName() {
        return name;
    } // Get file/directory name

    public void setName(String name) {
        this.name = name;
    } // Update file/directory name

    public boolean isDirectory() {
        return isDirectory;
    } // Check if it's a directory

    public String getContent() {
        return content;
    } // Get file content

    public void setContent(String content) {
        this.content = content;
    } // Update file content

    public Map<String, FileNode> getChildren() {
        return children;
    } // Get child nodes

    public FileNode getParent() {
        return parent;
    } // Get parent node

    public boolean isDeleted() {
        return isDeleted;
    } // Check if node is deleted

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    } // Mark node as deleted/restored
}

/**
 * FileManagementSystem implements an in-memory file system with basic file
 * operations
 * Supports operations like create, delete, rename, update, and restore
 * files/directories
 */
public class FileManagementSystem {
    private FileNode root; // Root directory of file system
    private List<FileNode> deletedNodes; // Trash bin for deleted items

    /**
     * Constructor initializes the file system with a root directory
     */
    public FileManagementSystem() { // Initialize file system
        root = new FileNode("root", true, null); // Create root directory
        deletedNodes = new ArrayList<>(); // Initialize trash bin
    }

    /**
     * Creates a new file or directory at the specified path
     */
    public void create(String path, boolean isDirectory) { // Create new file/directory (e.g., "documents/report.txt")
        String[] parts = path.split("/"); // Split path into parts ["documents", "report.txt"]
        FileNode current = root; // Start from root directory

        for (int i = 0; i < parts.length - 1; i++) { // Navigate through parent directories
            if (!current.getChildren().containsKey(parts[i])) { // Check if parent exists
                System.out.println("Parent directory does not exist!"); // Error if parent missing
                return;
            }
            current = current.getChildren().get(parts[i]); // Move to next directory
        }

        String name = parts[parts.length - 1]; // Get name of new item
        if (current.getChildren().containsKey(name)) { // Check if name already exists
            System.out.println("File/Directory already exists!"); // Error if duplicate
            return;
        }

        FileNode newNode = new FileNode(name, isDirectory, current); // Create new node
        current.getChildren().put(name, newNode); // Add to parent's children
        System.out.println((isDirectory ? "Directory" : "File") + " created successfully!");
    }

    /**
     * Lists all files and directories under the specified path
     */
    public void list(String path) { // List contents (e.g., "root" or "documents")
        FileNode node = findNode(path); // Find node at path
        if (node == null) { // Check if path exists
            System.out.println("Path not found!"); // Error if invalid path
            return;
        }
        printTree(node, 0); // Print contents recursively
    }

    /**
     * Helper method to recursively print the file system tree
     */
    private void printTree(FileNode node, int level) { // Helper to print tree structure
        if (node.isDeleted())
            return; // Skip deleted nodes

        String indent = "  ".repeat(level); // Create indentation based on level
        System.out.println(indent + (node.isDirectory() ? "[DIR] " : "[FILE] ") + node.getName());

        if (node.isDirectory()) { // If directory, print all children
            for (FileNode child : node.getChildren().values()) { // Iterate through children
                printTree(child, level + 1); // Recursive call for each child
            }
        }
    }

    /**
     * Updates the content of a file
     */
    public void updateContent(String path, String content) { // Update file content (e.g., "documents/report.txt")
        FileNode node = findNode(path); // Find file node
        if (node == null || node.isDirectory()) { // Check if file exists and is not directory
            System.out.println("File not found!"); // Error if invalid
            return;
        }
        node.setContent(content); // Update content
        System.out.println("Content updated successfully!");
    }

    /**
     * Renames a file or directory
     */
    public void rename(String path, String newName) { // Rename item (e.g., "documents/report.txt" to "summary.txt")
        FileNode node = findNode(path); // Find node to rename
        if (node == null) { // Check if exists
            System.out.println("Path not found!");
            return;
        }

        FileNode parent = node.getParent(); // Get parent node
        parent.getChildren().remove(node.getName()); // Remove old name from parent
        node.setName(newName); // Set new name
        parent.getChildren().put(newName, node); // Add with new name to parent
        System.out.println("Renamed successfully!");
    }

    /**
     * Deletes a file or directory
     */
    public void delete(String path) { // Delete item (e.g., "documents/report.txt")
        FileNode node = findNode(path); // Find node to delete
        if (node == null) { // Check if exists
            System.out.println("Path not found!");
            return;
        }

        node.setDeleted(true); // Mark as deleted
        deletedNodes.add(node); // Add to trash bin
        node.getParent().getChildren().remove(node.getName()); // Remove from parent
        System.out.println("Deleted successfully!");
    }

    /**
     * Restores a previously deleted file or directory
     */
    public void restore(String name) { // Restore deleted item (e.g., "report.txt")
        for (FileNode node : deletedNodes) { // Search in trash bin
            if (node.getName().equals(name)) { // If found
                node.setDeleted(false); // Mark as not deleted
                node.getParent().getChildren().put(name, node); // Add back to parent
                deletedNodes.remove(node); // Remove from trash bin
                System.out.println("Restored successfully!");
                return;
            }
        }
        System.out.println("Deleted item not found!"); // Error if not in trash
    }

    /**
     * Helper method to find a node in the file system tree
     */
    private FileNode findNode(String path) { // Find node by path (e.g., "documents/report.txt")
        if (path.equals("root"))
            return root; // Return root if requested

        String[] parts = path.split("/"); // Split path into parts
        FileNode current = root; // Start from root

        for (String part : parts) { // Navigate through path
            if (!current.getChildren().containsKey(part)) { // Check each part exists
                return null; // Return null if not found
            }
            current = current.getChildren().get(part); // Move to next node
        }
        return current; // Return found node
    }

    /**
     * Main method providing a command-line interface for the file system
     */
    public static void main(String[] args) { // Main program entry point
        FileManagementSystem fms = new FileManagementSystem(); // Create new file system
        Scanner scanner = new Scanner(System.in); // Create input scanner

        while (true) { // Main program loop
            System.out.println("\n=== File Management System ===");
            System.out.println("1. Create directory/file"); // Menu option 1
            System.out.println("2. List contents"); // Menu option 2
            System.out.println("3. Update file content"); // Menu option 3
            System.out.println("4. Rename directory/file"); // Menu option 4
            System.out.println("5. Delete directory/file"); // Menu option 5
            System.out.println("6. Restore deleted item"); // Menu option 6
            System.out.println("7. Exit"); // Menu option 7
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt(); // Read user choice
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Handle create operation
                    System.out.print("Enter path: ");
                    String createPath = scanner.nextLine();
                    System.out.print("Is it a directory? (true/false): ");
                    boolean isDir = scanner.nextBoolean();
                    fms.create(createPath, isDir);
                    break;

                case 2: // Handle list operation
                    System.out.print("Enter path to list (or 'root' for root): ");
                    String listPath = scanner.nextLine();
                    fms.list(listPath);
                    break;

                case 3: // Handle update operation
                    System.out.print("Enter file path: ");
                    String filePath = scanner.nextLine();
                    System.out.print("Enter new content: ");
                    String content = scanner.nextLine();
                    fms.updateContent(filePath, content);
                    break;

                case 4: // Handle rename operation
                    System.out.print("Enter path: ");
                    String renamePath = scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    fms.rename(renamePath, newName);
                    break;

                case 5: // Handle delete operation
                    System.out.print("Enter path to delete: ");
                    String deletePath = scanner.nextLine();
                    fms.delete(deletePath);
                    break;

                case 6: // Handle restore operation
                    System.out.print("Enter name to restore: ");
                    String restoreName = scanner.nextLine();
                    fms.restore(restoreName);
                    break;

                case 7: // Handle exit
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default: // Handle invalid input
                    System.out.println("Invalid option!");
            }
        }
    }
}

/*
 * Example Test Run:
 * === File Management System ===
 * 1. Create directory/file
 * 2. List contents
 * 3. Update file content
 * 4. Rename directory/file
 * 5. Delete directory/file
 * 6. Restore deleted item
 * 7. Exit
 * Choose an option: 1
 * Enter path: documents
 * Is it a directory? (true/false): true
 * Directory created successfully!
 * 
 * === File Management System ===
 * Choose an option: 1
 * Enter path: documents/report.txt
 * Is it a directory? (true/false): false
 * File created successfully!
 * 
 * === File Management System ===
 * Choose an option: 3
 * Enter file path: documents/report.txt
 * Enter new content: This is my first report content.
 * Content updated successfully!
 * 
 * === File Management System ===
 * Choose an option: 2
 * Enter path to list (or 'root' for root): root
 * [DIR] root
 * [DIR] documents
 * [FILE] report.txt
 * 
 * === File Management System ===
 * Choose an option: 5
 * Enter path to delete: documents/report.txt
 * Deleted successfully!
 * 
 * === File Management System ===
 * Choose an option: 2
 * Enter path to list (or 'root' for root): root
 * [DIR] root
 * [DIR] documents
 * 
 * === File Management System ===
 * Choose an option: 6
 * Enter name to restore: report.txt
 * Restored successfully!
 * 
 * === File Management System ===
 * Choose an option: 2
 * Enter path to list (or 'root' for root): root
 * [DIR] root
 * [DIR] documents
 * [FILE] report.txt
 * 
 * === File Management System ===
 * Choose an option: 7
 * Exiting...
 */