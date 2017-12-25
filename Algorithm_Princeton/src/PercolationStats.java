import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int n, trials;

	private double mean, stdDev, confLow, confHigh;

	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();

		this.n = n;
		this.trials = trials;

		// Let x[t] be the fraction of open sites in computational experiment t.
		double[] x = new double[trials];

		experiment(x);
		
		mean = StdStats.mean(x);
		stdDev = StdStats.stddev(x);
		confLow = mean - 1.96*stdDev/Math.sqrt(trials);
		confHigh = mean + - 1.96*stdDev/Math.sqrt(trials);
	}
	
	public double mean(){
		return mean;
	}
	
	public double stddev(){
		return stdDev;
	}
	
	public double confidenceLo(){
		return confLow;
	}
	
	public double confidenceHi(){
		return confHigh;
	}

	private void experiment(double[] x) {
		for (int i = 0; i < trials; i++) {
			Percolation p = new Percolation(n);
			while (!p.percolates()) {
				//open a random sites until percolated
				p.open(StdRandom.uniform(n)+1, StdRandom.uniform(n)+1); 
			}
			x[i]= ((double)p.numberOfOpenSites())/(n*n);
		}
	}
	
	public static void main(String[] args){
		if(args.length != 2){
			throw new IllegalArgumentException("Please enter grid size and number of trials");
		}
		
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		
		PercolationStats ps = new PercolationStats(n, t);
		StdOut.println("mean                    = "+ps.mean());
		StdOut.println("stddev                  = "+ps.stddev());
		StdOut.println("95% confidence interval = ["+ps.confidenceLo()+", "+ps.confidenceHi()+"]");
	}
}
