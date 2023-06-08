import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private final Board board;
	private final SearchNode solutionNode;
	
	private class SearchNode implements Comparable<SearchNode> {
		private final Board board;
		private final SearchNode previous;
		
		private final int moves;
		private final int priority;
		
		public SearchNode(Board board, SearchNode previousNode) {
			this.board = board;
			this.previous = previousNode;
			
			if (previous != null)
				this.moves = previous.moves + 1;
			else
				this.moves = 0;
			
			this.priority = board.manhattan() + moves;
		}
		
		@Override
		public int compareTo(Solver.SearchNode that) {
			return (this.priority - that.priority);    
		}
	}
	
    // Find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial == null) throw new IllegalArgumentException("Board can't be null");
    	
    	this.board = initial;
    	
    	MinPQ<SearchNode> searchPQ = new MinPQ<SearchNode>();
    	MinPQ<SearchNode> searchPQtwin = new MinPQ<SearchNode>();
    	searchPQ.insert(new SearchNode(board, null));
    	searchPQtwin.insert(new SearchNode(board.twin(), null));
    	
    	// A* Search
    	while (!searchPQ.min().board.isGoal() && !searchPQtwin.min().board.isGoal()) {	
    		SearchNode minNode = searchPQ.delMin();
    		SearchNode minNodeTwin = searchPQtwin.delMin();
    		
    		for (Board neighbor : minNode.board.neighbors()) {    			
    			if (minNode.previous == null || !neighbor.equals(minNode.previous.board))
    				searchPQ.insert(new SearchNode(neighbor, minNode));
    		}
    		
    		for (Board neighbor : minNodeTwin.board.neighbors()) {
    			if (minNodeTwin.previous == null || !neighbor.equals(minNodeTwin.previous.board))
    				searchPQtwin.insert(new SearchNode(neighbor, minNodeTwin));
    		}
    	}	
		
    	if (searchPQ.min().board.isGoal()) solutionNode = searchPQ.min();  	
    	else solutionNode = null;
    }

    // Is the initial board solvable? (see below)
    public boolean isSolvable() {
    	return !(solutionNode == null); 
    }

    // Min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	if (!isSolvable()) return -1;
   
    	return solutionNode.moves;
    }

    // Sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	if (!isSolvable()) return null;
    	
    	Stack<Board> solutions = new Stack<Board>();
    	
    	SearchNode node = solutionNode;
    	while (node != null) {
    		solutions.push(node.board);
    		node = node.previous;
    	}
    	
    	return solutions;
    }
    
    // Test client (see below) 
    public static void main(String[] args) {
    	// Create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
    	
        // Solve the puzzle
        Solver solver = new Solver(initial);

        // Print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
