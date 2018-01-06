import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board1D {
	private int[] blocks;
	private int n; // size of the block array
	private int hamming;
	private int manhattan;
	
	public Board1D(int[][] blocks) {
		this.n = blocks.length;
		this.blocks = new int[n*n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				this.blocks[i*n+j] = blocks[i][j];
			}
		}
		calculateHamming();
		calculateManhattan();
	}
	
	public int hamming() { // number of blocks out of place
		return hamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		return manhattan;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(n);
		result.append(System.getProperty("line.separator"));

		for (int i = 0; i < blocks.length; i++) {	
			if(i%n==0&&i>0) result.append(System.getProperty("line.separator"));
			result.append(String.format(" %1$2s", blocks[i]));
		}

		return result.toString();
	}

	private void calculateHamming(){
		int outOfPlace = -1;
		for (int i = 0; i < blocks.length; i++) {
			if(blocks[i] != i+1) outOfPlace++;
		}
		hamming = outOfPlace;
	}
	
	private void calculateManhattan(){
		int tDistance = 0;
		int row, col;

		for (int i = 0; i < blocks.length; i++) {
				if (blocks[i] != 0) {
					row = (blocks[i] - 1) / n;
					col = (blocks[i] - 1) % n;
					tDistance += Math.abs(row - i/n) + Math.abs(col - i%n);
				}
		}

		manhattan = tDistance;
	}
	
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board1D initial = new Board1D(blocks);

		StdOut.println(initial.toString());
		StdOut.println("hamming: " + initial.hamming());
		StdOut.println("manhattan: " + initial.manhattan());
//		StdOut.println("Neighbors:");
//		Iterable<Board> ib = initial.neighbors();
//		for (Board b : ib) {
//			StdOut.println(b.toString());
//			StdOut.println("Hamming: " + b.hamming());
//		}
		
//		StdOut.println("Twin:");
//		StdOut.println(initial.twin());
	}
}
