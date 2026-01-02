// Import Random class for generating random pieces
import java.util.Random;

public class TetrisPiece {
    // Define characters for piece representation
    private static final char FILLED_CELL = '#';
    private static final char EMPTY_CELL = '.';
    
    // Define all possible Tetris pieces (I, O, T, S, Z, J, L)
    // Each piece is represented as a 2D array where # represents filled cells
    private static final char[][][] TETROMINOS = {
        // I piece (long straight piece)
        {
            {FILLED_CELL, FILLED_CELL, FILLED_CELL, FILLED_CELL}
        },
        // O piece (square piece)
        {
            {FILLED_CELL, FILLED_CELL},
            {FILLED_CELL, FILLED_CELL}
        },
        // T piece (T-shaped piece)
        {
            {EMPTY_CELL, FILLED_CELL, EMPTY_CELL},
            {FILLED_CELL, FILLED_CELL, FILLED_CELL}
        },
        // S piece (S-shaped piece)
        {
            {EMPTY_CELL, FILLED_CELL, FILLED_CELL},
            {FILLED_CELL, FILLED_CELL, EMPTY_CELL}
        },
        // Z piece (Z-shaped piece)
        {
            {FILLED_CELL, FILLED_CELL, EMPTY_CELL},
            {EMPTY_CELL, FILLED_CELL, FILLED_CELL}
        },
        // J piece (J-shaped piece)
        {
            {FILLED_CELL, EMPTY_CELL, EMPTY_CELL},
            {FILLED_CELL, FILLED_CELL, FILLED_CELL}
        },
        // L piece (L-shaped piece)
        {
            {EMPTY_CELL, EMPTY_CELL, FILLED_CELL},
            {FILLED_CELL, FILLED_CELL, FILLED_CELL}
        }
    };
    
    // Current shape of the piece
    private char[][] shape;
    // Random number generator for piece selection
    private Random random;
    
    // Constructor - creates a random piece
    public TetrisPiece() {
        random = new Random();
        // Select a random piece from TETROMINOS array
        shape = TETROMINOS[random.nextInt(TETROMINOS.length)];
    }
    
    // Returns the current shape of the piece
    public char[][] getShape() {
        return shape;
    }
    
    // Returns the width of the current piece
    public int getWidth() {
        return shape[0].length;
    }
    
    // Returns the height of the current piece
    public int getHeight() {
        return shape.length;
    }
    
    // Returns what the piece would look like after rotation
    // This method creates a new rotated shape but doesn't modify the current piece
    public char[][] getRotatedShape() {
        int width = getWidth();
        int height = getHeight();
        // Create new array with swapped dimensions for rotation
        char[][] rotated = new char[width][height];
        
        // Perform 90-degree clockwise rotation
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Mathematical formula for 90-degree clockwise rotation:
                // new_x = j
                // new_y = height - 1 - i
                rotated[j][height - 1 - i] = shape[i][j];
            }
        }
        
        return rotated;
    }
    
    // Actually performs the rotation by updating the current shape
    public void rotate() {
        shape = getRotatedShape();
    }
} 