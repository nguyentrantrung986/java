import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

class Board {
	private int[][] blocks;
	private int n; // size of the block array
	private int hamming;
	private int manhattan;

	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		this.n = blocks.length;
		this.blocks = copy(blocks);
		this.hamming = -1;
		this.manhattan = -1;
	}

	public int dimension() { // board dimension n
		return n;
	}

	public int hamming() { // number of blocks out of place
		if (this.hamming == -1) {
			int outOfPlace = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (blocks[i][j] != i * n + j + 1)
						outOfPlace++;
				}
			}
			hamming = outOfPlace;
		}

		return hamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		if (manhattan == -1) {
			int tDistance = 0;
			int row, col;

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (blocks[i][j] != 0) {
						row = (blocks[i][j] - 1) / n;
						col = (blocks[i][j] - 1) % n;
						tDistance += Math.abs(row - i) + Math.abs(col - j);
					}
				}
			}

			manhattan = tDistance;
		}

		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				//last block should be 0
				int val = (i == n - 1 && j == n - 1) ? 0 : (i * n + j + 1); 
				if (blocks[i][j] != val)
					return false;
			}
		}

		return true;
	}

	// a board obtained by exchanging any pair of blocks, e.g. the first 2
	public Board twin() {
		if (n > 1) {
			int[][] twinBlock = copy(blocks);
			swap(twinBlock, 0, 0, 0, 1);

			return new Board(twinBlock);
		} else
			return this;
	}

	public boolean equals(Object y) {
		return y.toString().equals(this.toString());
	}

	public Iterable<Board> neighbors() {
		ArrayList<Board> alB = new ArrayList<Board>();
		int blankRow = 0;
		int blankCol = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] == 0) {
					blankRow = i;
					blankCol = j;
				}
			}
		}

		int[][] tmp = copy(blocks);

		if (blankRow > 0) { // swap the blank block with the block 1 row above
			swap(tmp, blankRow, blankCol, blankRow - 1, blankCol);
			alB.add(new Board(tmp));
			swap(tmp, blankRow, blankCol, blankRow - 1, blankCol);
		}

		if (blankCol > 0) { // swap the blank block with the block on the left
			swap(tmp, blankRow, blankCol, blankRow, blankCol - 1);
			alB.add(new Board(tmp));
			swap(tmp, blankRow, blankCol, blankRow, blankCol - 1);
		}

		if (blankRow < n - 1) { // swap the blank block with the block 1 row
								// below
			swap(tmp, blankRow, blankCol, blankRow + 1, blankCol);
			alB.add(new Board(tmp));
			swap(tmp, blankRow, blankCol, blankRow + 1, blankCol);
		}

		if (blankCol < n - 1) { // swap the blank block with the block on the
								// right
			swap(tmp, blankRow, blankCol, blankRow, blankCol + 1);
			alB.add(new Board(tmp));
		}

		return alB;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(n);
		result.append(System.getProperty("line.separator"));

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result.append(String.format(" %1$2s", blocks[i][j]));
			}
			result.append(System.getProperty("line.separator"));
		}

		return result.toString();
	}

	private void swap(int[][] a, int row1, int col1, int row2, int col2) {
		int tmp = a[row1][col1];
		a[row1][col1] = a[row2][col2];
		a[row2][col2] = tmp;
	}

	private int[][] copy(int[][] a) {
		int[][] c = new int[a.length][];
		for (int i = 0; i < a.length; i++) {
			c[i] = new int[a[i].length];
			for (int j = 0; j < a[i].length; j++) {
				c[i][j] = a[i][j];
			}
		}

		return c;
	}

	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		StdOut.println(initial.toString());
		StdOut.println("Manhattan: " + initial.manhattan());
		StdOut.println("Neighbors:");
		Iterable<Board> ib = initial.neighbors();
		for (Board b : ib) {
			StdOut.println(b.toString());
			StdOut.println("Manhattan: " + b.manhattan());
		}
	}
}
