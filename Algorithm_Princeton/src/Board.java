import java.util.ArrayList;

class Board {
	private int[][] blocks;
	private int n; // size of the block array
	private int hamming;
	private int manhattan;

	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		this.blocks = blocks.clone();
		this.n = blocks.length;
		this.hamming = -1;
		this.manhattan = -1;
	}

	public int dimension() { // board dimension n
		return blocks.length;
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

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int row = blocks[i][j] / n;
					int col = blocks[i][j] % n;

					tDistance = (row - i) + (col - j);
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
				if (blocks[i][j] != i * n + j + 1)
					return false;
			}
		}

		return true;
	}

	// a board obtained by exchanging any pair of blocks, e.g. the first 2
	public Board twin() {
		if (n > 1) {
			int[][] twinBlock = blocks.clone();
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

		int[][] tmp = blocks.clone();

		if (blankRow > 0) { // swap the blank block with the block 1 row above
			swap(tmp, blankRow, blankCol, blankRow - 1, blankCol);
			alB.add(new Board(tmp));
		}

		if (blankCol > 0) { // swap the blank block with the block on the left
			// undo to reuse, constructor will clone
			if (blankRow > 0) swap(tmp, blankRow, blankCol, blankRow - 1, blankCol); 
			swap(tmp, blankRow, blankCol, blankRow, blankCol - 1);
			alB.add(new Board(tmp));
		}
		
		if (blankRow < n - 1){ // swap the blank block with the block 1 row below
			if (blankCol > 0) swap(tmp, blankRow, blankCol, blankRow, blankCol - 1);
			swap(tmp, blankRow, blankCol, blankRow + 1, blankCol);
			alB.add(new Board(tmp));
		}
		
		if (blankCol < n - 1){	// swap the blank block with the block on the right
			if (blankRow < n - 1) swap(tmp, blankRow, blankCol, blankRow + 1, blankCol);
			swap(tmp, blankRow, blankCol, blankRow, blankCol + 1);
			alB.add(new Board(tmp));			
		}

		return alB;
	}

	private void swap(int[][] a, int row1, int col1, int row2, int col2) {
		int tmp = a[row1][col1];
		a[row1][col1] = a[row2][col2];
		a[row2][col2] = tmp;
	}
}
