import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
	private final int dim; // Dimension
	private boolean[][] grid; // dim * dim grid. 0 = closed, 1 = open
	
	private final int virtualTopIndex; // dim - 2
	private final int virtualBottomIndex; // dim - 1
	
	private final int sites;
	private int openSites;
	private final WeightedQuickUnionUF connections, flow;
	
	// Creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n <= 0) {
            throw new IllegalArgumentException("Input must be greater than 0");  
        }
    	
    	dim = n;
    	sites = n*n;
    	openSites = 0;
    	
    	grid = new boolean[dim][dim];
    	
    	// +2 for virtual top and bottom sites
    	connections = new WeightedQuickUnionUF((sites)+2);
    	// +1 for virtual top flow source site
    	flow = new WeightedQuickUnionUF((sites)+1);
    	
    	// Final two values of connections are virtual top and bottom site
    	virtualTopIndex = sites;
    	virtualBottomIndex = sites+1;
    }
    
    // Opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	if ((row < 1 || row > dim) || (col < 1 || col > dim)) {
            throw new IllegalArgumentException("Index out of range for grid");  
        }
    	// Site already open so can return
    	if (grid[row-1][col-1]) return;

    	
    	int index = rowColToIndex(row, col);
    	int neighbourIndex;
    	
    	// Connect to virtual sites if necessary
    	if (row == 1) {
    		connections.union(virtualTopIndex, index);
    		flow.union(virtualTopIndex, index);
    	}
    	if (row == dim) {
    		connections.union(virtualBottomIndex, index);
    	}
    	
    	// Creating union with neighbour sites if they are open
    	if (row > 1 && (grid[row-2][col-1])) {
    		neighbourIndex = (row-2)*dim + (col-1);
    		connections.union(index, neighbourIndex);
    		flow.union(index, neighbourIndex);
    	}
    	if (col > 1 && (grid[row-1][col-2])) {
    		neighbourIndex = (row-1)*dim + (col-2);
    		connections.union(index, neighbourIndex);
    		flow.union(index, neighbourIndex);
    	}
    	if (col < dim && (grid[row-1][col])) {
    		neighbourIndex = (row-1)*dim + (col);
    		connections.union(index, neighbourIndex);
    		flow.union(index, neighbourIndex);
    	}
    	if (row < dim && (grid[row][col-1])) {
    		neighbourIndex = (row)*dim + (col-1);
    		connections.union(index, neighbourIndex);
    		flow.union(index, neighbourIndex);
    	}
    	
    	grid[row-1][col-1] = true;
    	openSites++;   	
    }
    
    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	if ((row < 1 || row > dim) || (col < 1 || col > dim)) {
            throw new IllegalArgumentException("Index out of range for grid");  
        }
    	
    	return (grid[row-1][col-1]);
    }

    // Is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	if ((row < 1 || row > dim) || (col < 1 || col > dim)) {
            throw new IllegalArgumentException("Index out of range for grid");  
        }
    	
    	int index = rowColToIndex(row, col);
    	return (flow.find(index) == flow.find(virtualTopIndex));
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
    	return openSites;
    }

    // Does the system percolate?
    public boolean percolates() {
    	if (connections.find(virtualTopIndex) == connections.find(virtualBottomIndex)) {
    		return true;
    	}
    	return false;
    }
    
    // Converts row and column (2d) to a 1d index
    private int rowColToIndex(int row, int col) {
    	int index = (row-1)*dim + (col-1);
    	
    	return index;
    }
    
    // Test client (optional)
    public static void main(String[] args) {
    	int size = 1000;
    	int sites = size*size;	
    	
    	double startTime = System.currentTimeMillis();

    	Percolation p = new Percolation(size);
    	
    	while(p.percolates() == false) {
    		int nextRow = StdRandom.uniform(1, p.dim+1); 
        	int nextCol = StdRandom.uniform(1, p.dim+1); 
        	
        	p.open(nextRow, nextCol);
    	}

    	double endTime = System.currentTimeMillis();
    	
    	int openSites = p.numberOfOpenSites();
    	float probCross = (float)openSites / (sites);
    	
    	System.out.println(openSites +" out of "+ sites +" sites are open.");
    	System.out.println("Probability: "+ probCross);
    	System.out.println("Time: "+ (endTime-startTime) +"ms");
  
    }
}
