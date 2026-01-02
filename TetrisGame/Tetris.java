import java.util.*;

public class Tetris {
    // Define constants for the game board dimensions
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;
    // Define characters for empty and filled cells on the board
    private static final char EMPTY_CELL = '.';
    private static final char FILLED_CELL = '#';
    
    // Game state variables
    private char[][] board;           // 2D array to store the game board
    private int currentScore;         // Keep track of player's score
    private boolean gameOver;         // Flag to indicate if game is over
    private TetrisPiece currentPiece; // Current falling piece
    private int currentX;             // Current X position of falling piece
    private int currentY;             // Current Y position of falling piece
    
    // Constructor to initialize the game
    public Tetris() {
        // Create the game board with specified dimensions
        board = new char[BOARD_HEIGHT][BOARD_WIDTH];
        // Initialize the board with empty cells
        initializeBoard();
        // Set initial score to 0
        currentScore = 0;
        // Set game over flag to false
        gameOver = false;
    }
    
    // Method to fill the board with empty cells
    private void initializeBoard() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }
    
    // Main game loop method
    public void start() {
        // Create scanner object for user input
        Scanner scanner = new Scanner(System.in);
        // Create first piece to start the game
        spawnNewPiece();
        
        // Continue game until gameOver becomes true
        while (!gameOver) {
            // Show current state of the game board
            displayBoard();
            // Display current score
            System.out.println("Score: " + currentScore);
            // Show available commands to the player
            System.out.println("Commands: a (left), d (right), s (down), w (rotate), q (quit)");
            System.out.print("Enter command: ");
            
            // Get player's input and convert to lowercase
            String command = scanner.nextLine().toLowerCase();
            
            // Process player's command
            switch (command) {
                case "a":
                    moveLeft();    // Move piece left
                    break;
                case "d":
                    moveRight();   // Move piece right
                    break;
                case "s":
                    // Try to move piece down
                    if (!moveDown()) {
                        placePiece();    // If can't move down, place the piece
                        clearLines();     // Check and clear completed lines
                        spawnNewPiece();  // Create new falling piece
                    }
                    break;
                case "w":
                    rotate();     // Rotate piece
                    break;
                case "q":
                    gameOver = true;  // End the game
                    break;
            }
        }
        
        // Show final score when game ends
        System.out.println("Game Over! Final Score: " + currentScore);
        scanner.close();
    }
    
    // Method to display the current state of the game board
    private void displayBoard() {
        // Create temporary board for display (includes falling piece)
        char[][] tempBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
        // Copy current board state
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            tempBoard[i] = board[i].clone();
        }
        
        // Add current falling piece to temporary board
        if (currentPiece != null) {
            char[][] shape = currentPiece.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] == FILLED_CELL) {
                        int y = currentY + i;
                        int x = currentX + j;
                        // Only draw if within board boundaries
                        if (y >= 0 && y < BOARD_HEIGHT && x >= 0 && x < BOARD_WIDTH) {
                            tempBoard[y][x] = FILLED_CELL;
                        }
                    }
                }
            }
        }
        
        // Clear console for clean display
        //it creates 50 blank lines
        //This pushes the previous content up, making it appear like the screen has been cleared
        System.out.println("\n".repeat(50));
        // Draw top border
        System.out.println("-".repeat(BOARD_WIDTH + 2));
        // Draw board with side borders
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            System.out.print("|");
            for (int j = 0; j < BOARD_WIDTH; j++) {
                System.out.print(tempBoard[i][j]);
            }
            System.out.println("|");
        }
        // Draw bottom border
        System.out.println("-".repeat(BOARD_WIDTH + 2));
    }
    
    // Method to create a new falling piece
    private void spawnNewPiece() {
        // Create new random piece
        currentPiece = new TetrisPiece();
        // Center the piece horizontally
        currentX = BOARD_WIDTH / 2 - currentPiece.getWidth() / 2;
        // Start from top
        currentY = 0;
        
        // Check if new piece can be placed (if not, game is over)
        if (!isValidMove(currentX, currentY, currentPiece.getShape())) {
            gameOver = true;
        }
    }
    
    // Method to check if a move is valid (no collisions or out of bounds)
    private boolean isValidMove(int newX, int newY, char[][] shape) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == FILLED_CELL) {
                    //L-piece shape:
                    //. . #    (i=0, j=0,1,2)
                    //# # #    (i=1, j=0,1,2)
                    //newX = 5 (piece's left edge is at column 5)
                    //newY = 3 (piece's top edge is at row 3)
                    int x = newX + j;
                    int y = newY + i;
                    
                    // Check if move would put piece out of bounds
                    if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) {
                        return false;
                    }
                    
                    // Check if move would collide with placed pieces
                    if (y >= 0 && board[y][x] == FILLED_CELL) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    // Method to move piece left if possible
    private void moveLeft() {
        if (isValidMove(currentX - 1, currentY, currentPiece.getShape())) {
            currentX--;
        }
    }
    
    // Method to move piece right if possible
    private void moveRight() {
        if (isValidMove(currentX + 1, currentY, currentPiece.getShape())) {
            currentX++;
        }
    }
    
    // Method to move piece down if possible
    private boolean moveDown() {
        if (isValidMove(currentX, currentY + 1, currentPiece.getShape())) {
            currentY++;
            return true;
        }
        return false;
    }
    
    // Method to rotate piece if possible
    private void rotate() {
        char[][] rotatedShape = currentPiece.getRotatedShape();
        if (isValidMove(currentX, currentY, rotatedShape)) {
            currentPiece.rotate();
        }
    }
    
    // Method that fixes/places the current Tetris piece onto the game board
    private void placePiece() {
        // Get the current piece's shape matrix from TetrisPiece class
        char[][] shape = currentPiece.getShape();
        
        // Loop through each row of the piece's shape
        for (int i = 0; i < shape.length; i++) {
            // Loop through each column in the current row
            for (int j = 0; j < shape[i].length; j++) {
                // Check if the current cell in the piece's shape is filled (#)
                if (shape[i][j] == FILLED_CELL) {
                    // Calculate the absolute Y position on the board (piece's Y position + relative row)
                    int y = currentY + i;
                    // Calculate the absolute X position on the board (piece's X position + relative column)
                    int x = currentX + j;
                    
                    // Only place the piece if it's within the board boundaries
                    if (y >= 0 && y < BOARD_HEIGHT && x >= 0 && x < BOARD_WIDTH) {
                        // Mark the cell on the actual game board as filled (#)
                        board[y][x] = FILLED_CELL;
                    }
                }
            }
        }
    }
    
    // Method to check and clear completed lines
    private void clearLines() {
        int linesCleared = 0;
        
        // Check each line from bottom to top
        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean isLineFull = true;
            // Check if line is completely filled
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    isLineFull = false;
                    break;
                }
            }
            
            // If line is full, clear it and move everything down
            if (isLineFull) {
                linesCleared++;
                // Move all lines above down one position
                for (int k = i; k > 0; k--) {
                    board[k] = board[k - 1].clone();
                }
                // Clear top line
                Arrays.fill(board[0], EMPTY_CELL);
                i++; // Check the same line again as everything moved down
            }
        }
        
        // Update score based on number of lines cleared
        // Score increases exponentially with more lines cleared at once
        if (linesCleared > 0) {
            currentScore += Math.pow(2, linesCleared - 1) * 100;
        }
    }
    
    // Main method to start the game
    public static void main(String[] args) {
        Tetris game = new Tetris();
        game.start();
    }
} 