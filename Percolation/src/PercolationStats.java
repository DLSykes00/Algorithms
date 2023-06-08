import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

	private final int trials;
	private final double[] results;
	
    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if ((n <= 0) || (trials <= 0)) {
            throw new IllegalArgumentException("Invalid input arguments");  
        }
    	
    	this.trials = trials;
    	results = new double[trials];
    	
    	for (int i = 0; i < trials; i++) {
    		Percolation p = new Percolation(n);
    		
	    	while (!p.percolates()) {
	    		int nextRow = StdRandom.uniform(1, n+1); 
	        	int nextCol = StdRandom.uniform(1, n+1); 
	        	
	        	p.open(nextRow, nextCol);
	    	}
	    	double probCross = (double)p.numberOfOpenSites() / (n*n);
	    	results[i] = probCross;
    	} 	
    }

    // Sample mean of percolation threshold
    public double mean() {
    	double total = 0.0;
    	double mean = 0.0;
    	
    	for (double result : results) {
    		total += result;	
    	}
    	mean = total/trials;
    	
    	return mean;
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
    	double mean = mean();
    	double stddev = 0.0;
    	double total = 0.0;
    	
    	for (double result : results) {
    		total += Math.pow(result-mean, 2);	
    	}
    	stddev = Math.sqrt(total / (trials-1));
    	
    	return stddev;
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
    	double mean = mean();
    	double stddev = stddev();
    	double lo = 0.0;
    	
    	lo = (mean - ((1.96*stddev)/(Math.sqrt(trials))));
    	
    	return lo;
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
    	double mean = mean();
    	double stddev = stddev();
    	double hi = 0.0;
    	
    	hi = (mean + ((1.96*stddev)/(Math.sqrt(trials))));
    	
    	return hi;
    }

   // Test client (see below)
    public static void main(String[] args) {
    	int n, trials;
    	
		try {
			n = Integer.parseInt(args[0]);
			trials = Integer.parseInt(args[1]);
	    } catch (NumberFormatException e) { 
	    	throw new IllegalArgumentException(e);  
	    }	
	   
		PercolationStats stats = new PercolationStats(n, trials);
		System.out.println("Mean: " + stats.mean());
		System.out.println("SD: " + stats.stddev());
		System.out.println("95% CI: [" + stats.confidenceLo() + ", " + stats.confidenceHi() +"]");
   }
}
