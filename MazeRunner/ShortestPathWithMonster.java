import java.util.*;

public class ShortestPathWithMonster {
    // Direction arrays for all 8 possible movements (including diagonals)
    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    
    static class Point {
        int x, y, dist;
        Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
    
    public static int findShortestPath(char[][] matrix, int n) {
        // Find positions of A and D
        Point start = null, end = null;

        // start will store: Point(3, 2, 0) because 'A' is at row 3, column 2
        // end will store: Point(1, 4, 0) because 'D' is at row 1, column 4
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == 'A') 
                    start = new Point(i, j, 0);
                else if(matrix[i][j] == 'D')
                    end = new Point(i, j, 0);
            }
        }
        
        // If either start or end is not found
        if(start == null || end == null) 
            return -1;
            
        // BFS implementation
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        
        // Queue looks like: [(3,2,0)]
        queue.add(start);

        // visited[3][2] = true
        // F F F F F
        // F F F F F
        // F F T F F    // Position (3,2) is marked as true
        // F F F F F
        visited[start.x][start.y] = true;   
        
        while(!queue.isEmpty()) {
            // current = Point(3, 2, 0)  // Original 'A' position
            Point current = queue.poll();
            
            // If destination is reached
            if(current.x == end.x && current.y == end.y) 
                return current.dist;
                
            // Try all 8 directions
            for(int i = 0; i < 8; i++) {
                // newX = 3 + dx[0] = 3 + (-1) = 2
                // newY = 2 + dy[0] = 2 + (-1) = 1
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];
                
                // Check if new position is valid
                if(isValid(newX, newY, n) && !visited[newX][newY] && matrix[newX][newY] != 'M') {
                    visited[newX][newY] = true;
                    queue.add(new Point(newX, newY, current.dist + 1));
                }
            }
        }
        
        // If destination cannot be reached
        return -1;
    }
    
    static boolean isValid(int x, int y, int n) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Input matrix size
        System.out.println("Enter matrix size (n x n):");
        int n = sc.nextInt();
        
        char[][] matrix = new char[n][n];
        
        // Initialize matrix with '0'
        for(int i = 0; i < n; i++) {
            Arrays.fill(matrix[i], '0');
        }
        
        // Input adventurer position
        System.out.println("Enter Adventurer (A) position (x y):");
        int ax = sc.nextInt();
        int ay = sc.nextInt();
        matrix[ax][ay] = 'A';
        
        // Input destination position
        System.out.println("Enter Destination (D) position (x y):");
        int dx = sc.nextInt();
        int dy = sc.nextInt();
        matrix[dx][dy] = 'D';
        
        // Input number of monsters
        System.out.println("Enter number of monsters:");
        int m = sc.nextInt();
        
        // Input monster positions
        System.out.println("Enter Monster positions (x y):");
        for(int i = 0; i < m; i++) {
            int mx = sc.nextInt();
            int my = sc.nextInt();
            matrix[mx][my] = 'M';
        }
        
        // Print the matrix
        System.out.println("\nMatrix:");
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        
        // Find and print the result
        int result = findShortestPath(matrix, n);
        if(result != -1) {
            System.out.println("\nThe shortest path = " + result);
        } else {
            System.out.println("\nA will die as A can't reach D.");
        }
        
        sc.close();
    }
}
