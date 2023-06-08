import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	public static void main(String[] args) {
		final int k;
    	
		try {
			k = Integer.parseInt(args[0]);
	    } catch (NumberFormatException e) { 
	    	throw new IllegalArgumentException(e);  
	    }	
		
		if (StdIn.isEmpty()) return;
		
		RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
		
		while (!StdIn.isEmpty()) {
			rQueue.enqueue(StdIn.readString());
		}
		if (k > rQueue.size()) throw new IllegalArgumentException("k must be <= input sequence size");
		
		// Dequeue and output to console
		for (int i = 0; i < k; i++)
		{
			System.out.println(rQueue.dequeue());
		}
	}
}
