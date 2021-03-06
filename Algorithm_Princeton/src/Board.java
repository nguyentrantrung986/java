import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int[] blocks;
	private int n; // size of the block array
	private int hamming;
	private int manhattan;

	public Board(int[][] blocks) {
		this.n = blocks.length;
		this.blocks = new int[n * n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				this.blocks[i * n + j] = blocks[i][j];
			}
		}
		calculateHamming();
		calculateManhattan();
	}

	public int dimension() { // board dimension n
		return n;
	}

	public int hamming() { // number of blocks out of place
		return hamming;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < blocks.length - 1; i++) {
			// last block should be 0
			if (blocks[i] != i + 1)
				return false;
		}

		return true;
	}

	// a board obtained by exchanging any pair of non-empty blocks
	public Board twin() {
		if (n > 1) {
			int[][] twinBlock = copyTo2DBlocks(blocks);

			if (twinBlock[0][0] != 0 && twinBlock[0][1] != 0)
				swap(twinBlock, 0, 0, 0, 1);
			else
				swap(twinBlock, 1, 0, 1, 1);

			return new Board(twinBlock);
		} else
			return this;
	}

	public boolean equals(Object y) {
		if (y == null)
			return false;

		return y.toString().equals(this.toString());
	}

	public Iterable<Board> neighbors() {
		ArrayList<Board> alB = new ArrayList<Board>();
		int blankRow = 0;
		int blankCol = 0;

		int[][] tmp = copyTo2DBlocks(blocks);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tmp[i][j] == 0) {
					blankRow = i;
					blankCol = j;
				}
			}
		}

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

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		return manhattan;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(n);
		result.append(System.getProperty("line.separator"));

		for (int i = 0; i < blocks.length; i++) {
			if (i % n == 0 && i > 0)
				result.append(System.getProperty("line.separator"));
			result.append(String.format(" %1$2s", blocks[i]));
		}

		return result.toString();
	}

	private void calculateHamming() {
		int outOfPlace = -1;
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != i + 1)
				outOfPlace++;
		}
		hamming = outOfPlace;
	}

	private void calculateManhattan() {
		int tDistance = 0;
		int row, col;

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != 0) {
				row = (blocks[i] - 1) / n;
				col = (blocks[i] - 1) % n;
				tDistance += Math.abs(row - i / n) + Math.abs(col - i % n);
			}
		}

		manhattan = tDistance;
	}

	private void swap(int[][] a, int row1, int col1, int row2, int col2) {
		int tmp = a[row1][col1];
		a[row1][col1] = a[row2][col2];
		a[row2][col2] = tmp;
	}

	private int[][] copyTo2DBlocks(int[] a) {
		int[][] twinBlock = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				twinBlock[i][j] = a[i * n + j];
			}
		}

		return twinBlock;
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
		StdOut.println("hamming: " + initial.hamming());
		StdOut.println("manhattan: " + initial.manhattan());
		StdOut.println("Neighbors:");
		Iterable<Board> ib = initial.neighbors();
		for (Board b : ib) {
			StdOut.println(b.toString());
			StdOut.println("Hamming: " + b.hamming());
			StdOut.println("manhattan: " + b.manhattan());
		}

		StdOut.println("Twin:");
		StdOut.println(initial.twin());
	}
}
