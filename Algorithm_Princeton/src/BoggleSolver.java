import java.util.HashSet;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.TrieST;

public class BoggleSolver {
	TrieST<Integer> dict;

	/**
	 * Initializes the data structure using the given array of strings as the
	 * dictionary. (You can assume each word in the dictionary contains only the
	 * uppercase letters A through Z.)
	 **/
	public BoggleSolver(String[] dictionary) {
		dict = new TrieST<>();
		for (String s : dictionary)
			dict.put(s, 0);
	}

	// Returns the set of all valid words in the given Boggle board, as an
	// Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		HashSet<String> allStrings = new HashSet<>();
		boolean[][] marked = new boolean[board.rows()][board.cols()];
		for (int row = 0; row < board.rows(); row++)
			for (int col = 0; col < board.cols(); col++) {
				getStrings(board, row, col, marked, "", allStrings);
			}

		return allStrings;
	}

	private void getStrings(BoggleBoard board, int row, int col, boolean[][] marked, String prefix,
			HashSet<String> allStrings) {
		marked[row][col] = true;
		prefix += board.getLetter(row, col);
		Queue<String> q = (Queue<String>)dict.keysWithPrefix(prefix);
		if(q.isEmpty())
			return;
		
		allStrings.add(prefix);

		// recursive calls in clockwise order of adjacent dice
		if (row > 0 && !marked[row - 1][col])
			getStrings(board, row - 1, col, marked, prefix, allStrings);
		if (row > 0 && col < board.cols() - 1 && !marked[row - 1][col + 1])
			getStrings(board, row - 1, col + 1, marked, prefix, allStrings);
		if (col < board.cols() - 1 && !marked[row][col + 1])
			getStrings(board, row, col + 1, marked, prefix, allStrings);
		if (row < board.rows() - 1 && col < board.cols() - 1 && !marked[row + 1][col + 1])
			getStrings(board, row + 1, col + 1, marked, prefix, allStrings);
		if (row < board.rows() - 1 && !marked[row + 1][col])
			getStrings(board, row + 1, col, marked, prefix, allStrings);
		if (row < board.rows() - 1 && col > 0 && !marked[row + 1][col - 1])
			getStrings(board, row + 1, col - 1, marked, prefix, allStrings);
		if (col > 0 && !marked[row][col - 1])
			getStrings(board, row, col - 1, marked, prefix, allStrings);
		if (row > 0 && col > 0 && !marked[row - 1][col - 1])
			getStrings(board, row - 1, col - 1, marked, prefix, allStrings);

		marked[row][col] = false;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		char[][] letters = {{'A','B','C'},{'D','E','F'},{'G','H','I'}};
		BoggleBoard b = new BoggleBoard(letters);
		System.out.println(b.toString());
		String[] dict = { "ABC" };
		BoggleSolver bs = new BoggleSolver(dict);
		Stopwatch sw = new Stopwatch();
		Iterable<String> aStr = bs.getAllValidWords(b);
		System.out.println(sw.elapsedTime());
		 for(String s:aStr)
		 System.out.println(s);
	}
}
