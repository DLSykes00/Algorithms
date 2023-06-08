import java.util.ArrayList;

public class Board {

	private final int n; // Dimension of board
	private int[][] tiles;

    // Create a board from an n-by-n array of tiles,
    // Where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	this.n = tiles.length;
    	this.tiles = copyTiles(tiles);
    }
                           
    private int[][] copyTiles(int[][] t) {
    	int[][] copy = new int[n][n];
    	
    	for (int i = 0; i < n; i++)
    		for (int j = 0; j < n; j++)
    			copy[i][j] = t[i][j];
    	
    	return copy;
    }
    
    private int[] findSpace(Board b) { // Returns array with space index [i, j]
    	int[] space = new int[2];
    	
    	for (int i = 0; i < n; i++)
    		for (int j = 0; j < n; j++)
    			if (b.tiles[i][j] == 0) {
    				space[0] = i;
    				space[1] = j;
    			}
    	
    	return space;
    }
    
    // String representation of this board
    public String toString() {
    	StringBuilder output = new StringBuilder();
    	
    	output.append(String.format("%s\n", Integer.toString(tiles.length)));
    	
    	for (int[] row : tiles) {
    		for (int tile : row) {
    			output.append(String.format(" %d", tile));
    		}
    		output.append("\n");
    	}
    	
    	return output.toString();
    }

    // Board dimension n
    public int dimension() {
    	return n;
    }

    // Number of tiles out of place
    public int hamming() {
    	int ham = 0;
    	
    	for (int i = 0; i < n; i++) { // Check each element is equal to its index in array + 1
    		for (int j = 0; j < n; j++) {
    			if (tiles[i][j] != n * i + j + 1) ham++;
    		}
    	}
    	ham--; // Stop blank space from being taken into account
    	
    	return ham;
    }

    // Sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int man = 0;
    	
    	for (int i = 0; i < n; i++) { // Check each element is equal to its index in array + 1;
    		for (int j = 0; j < n; j++) {
    			if (tiles[i][j] == 0) continue; // Ignore blank space
        			
    			man += Math.abs((tiles[i][j] - 1) / n - i); // Row offset
    			man += Math.abs((tiles[i][j] - 1) % n - j); // Col offset
    		}
    	}
    	
    	return man;
    }

    // Is this board the goal board
    public boolean isGoal() {
    	if (hamming() == 0) return true;
    	return false;
    }

    // Does this board equal y
    public boolean equals(Object y) {
    	if (y == this) return true;
    	if (y == null) return false;
    	if (y.getClass() != this.getClass()) return false;
    	
    	Board that = (Board) y;
    	if (this.n == that.n) {
    		for (int i = 0; i < n; i++) { // Check each element is equal to its index in array + 1;
        		for (int j = 0; j < n; j++) {
        			if (this.tiles[i][j] != that.tiles[i][j]) return false;
        		}
        	}
    		
    		return true;
    	}
    	
    	return false;
    }

    // All neighbouring boards
    public Iterable<Board> neighbors() {
    	ArrayList<Board> neighbors = new ArrayList<Board>();
    	int[] space = findSpace(this); // [i, j] of space in tiles
    	
    	if (space[0] != 0) { // Swap space with value in row above if space not in top row
    		Board neighbor = new Board(tiles);
    		neighbor.tiles[space[0]][space[1]] = neighbor.tiles[space[0] - 1][space[1]]; 
    		neighbor.tiles[space[0] - 1][space[1]] = 0; 
    		neighbors.add(neighbor);
    	}
    	if (space[0] != n - 1) { // Swap space with value in row below if space not in bottom row
    		Board neighbor = new Board(tiles);
    		neighbor.tiles[space[0]][space[1]] = neighbor.tiles[space[0] + 1][space[1]]; 
    		neighbor.tiles[space[0] + 1][space[1]] = 0; 
    		neighbors.add(neighbor);
    	}
    	if (space[1] != 0) { // Swap space with value in column to the left if space not in left column
    		Board neighbor = new Board(tiles);
    		neighbor.tiles[space[0]][space[1]] = neighbor.tiles[space[0]][space[1] - 1]; 
    		neighbor.tiles[space[0]][space[1] - 1] = 0; 
    		neighbors.add(neighbor);
    	}
    	if (space[1] != n - 1) { // Swap space with value in column to the right if space not in right column
    		Board neighbor = new Board(tiles);
    		neighbor.tiles[space[0]][space[1]] = neighbor.tiles[space[0]][space[1] + 1]; 
    		neighbor.tiles[space[0]][space[1] + 1] = 0; 
    		neighbors.add(neighbor);
    	}
    	
    	return neighbors;
    }

    // A board that is obtained by exchanging a pair of tiles
    public Board twin() {
    	Board twin = new Board(tiles);
    	
    	if (twin.tiles[0][0] != 0 && twin.tiles[1][0] != 0) {
    		int swapTile = twin.tiles[0][0];
    		twin.tiles[0][0] = twin.tiles[1][0];
    		twin.tiles[1][0] = swapTile;
    	} else {
    		int swapTile = twin.tiles[0][1];
    		twin.tiles[0][1] = twin.tiles[1][1];
    		twin.tiles[1][1] = swapTile;
    	}
    	
    	return twin;
    }

    // Unit testing (not graded)
    public static void main(String[] args) {
    	int[][] tiles = new int[][] 
    	{
    		{6, 2, 3},
    		{4, 5, 1},
    		{7, 8, 0}
    	};
    	Board b = new Board(tiles);
    	
    	System.out.println(b);
    	System.out.println(b.hamming());
    	System.out.println(b.manhattan());
    	System.out.println(b.twin());
    	
    	for (Board bd : b.neighbors()) {
    		System.out.println(bd);
    	}
    }
}
