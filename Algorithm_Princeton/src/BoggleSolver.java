import java.util.HashSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class BoggleSolver {
	private final AlphabetTrieST<Integer> dict;

	/**
	 * Initializes the data structure using the given array of strings as the
	 * dictionary. (You can assume each word in the dictionary contains only the
	 * uppercase letters A through Z.)
	 **/
	public BoggleSolver(String[] dictionary) {
		dict = new AlphabetTrieST<>();
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

		char c = board.getLetter(row, col);
		prefix += c;
		if (c == 'Q')
			prefix += 'U';

		if (!dict.hasKeysWithPrefix(prefix)) {
			marked[row][col] = false;
			return;
		}

		if (prefix.length() > 2 && dict.contains(prefix))
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

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through
	// Z.)
	public int scoreOf(String word) {
		if (word == null)
			throw new IllegalArgumentException();
		if (!dict.contains(word))
			return 0;
		int len = word.length();
		if (len < 3)
			return 0;
		else if (len < 5)
			return 1;
		else if (len == 5)
			return 2;
		else if (len == 6)
			return 3;
		else if (len == 7)
			return 5;
		else
			return 11;
	}

	// Tries with radix 26 for uppercase letters (ascii A = 65)
	private static class AlphabetTrieST<Value> {
		private Node root; // root of trie
		private int n; // number of keys in trie
		private static final int R = 26;

		public AlphabetTrieST() {
		}

		public boolean hasKeysWithPrefix(String prefix) {
			Node x = get(root, prefix, 0);
			return hasKeysWithPrefix(x);
		}

		private boolean hasKeysWithPrefix(Node x) {
			if (x == null)
				return false;
			boolean found = false;
			if (x.val != null)
				return true;

			for (int i = 0; i < R; i++) {
				found = hasKeysWithPrefix(x.next[i]);
				if (found)
					break;
			}
			return found;
		}

		public Value get(String key) {
			if (key == null)
				throw new IllegalArgumentException("argument to get() is null");
			Node x = get(root, key, 0);
			if (x == null)
				return null;
			return (Value) x.val;
		}

		private Node get(Node x, String key, int d) {
			if (x == null)
				return null;
			if (d == key.length())
				return x;
			char c = key.charAt(d);
			return get(x.next[c - 65], key, d + 1);
		}

		public boolean contains(String key) {
			if (key == null)
				throw new IllegalArgumentException("argument to contains() is null");
			return get(key) != null;
		}

		public void put(String key, Value val) {
			if (key == null)
				throw new IllegalArgumentException("first argument to put() is null");
			if (val == null)
				delete(key);
			else
				root = put(root, key, val, 0);
		}

		private Node put(Node x, String key, Value val, int d) {
			if (x == null)
				x = new Node();
			if (d == key.length()) {
				if (x.val == null)
					n++;
				x.val = val;
				return x;
			}
			char c = key.charAt(d);
			x.next[c - 65] = put(x.next[c - 65], key, val, d + 1);
			return x;
		}

		public void delete(String key) {
			if (key == null)
				throw new IllegalArgumentException("argument to delete() is null");
			root = delete(root, key, 0);
		}

		private Node delete(Node x, String key, int d) {
			if (x == null)
				return null;
			if (d == key.length()) {
				if (x.val != null)
					n--;
				x.val = null;
			} else {
				char c = key.charAt(d);
				x.next[c - 65] = delete(x.next[c - 65], key, d + 1);
			}

			// remove subtrie rooted at x if it is completely empty
			if (x.val != null)
				return x;
			for (int c = 0; c < R; c++)
				if (x.next[c - 65] != null)
					return x;
			return null;
		}

		// 26-way trie node
		private static class Node {
			private Object val;
			private Node[] next = new Node[R];
		}
	}

	public static void main(String[] args) {
		// char[][] letters = { { 'P', 'Y', 'O' }, { 'I', 'N', 'U' }, { 'D',
		// 'S', 'E' } };
		// char[][] letters = {{'N','U'},{'S','E'}};
		// BoggleBoard b = new BoggleBoard(letters);
		// System.out.println(b.toString());
		// String[] dict = { "END", "ENDS" };
		// BoggleSolver bs = new BoggleSolver(dict);
		// Stopwatch sw = new Stopwatch();
		// Iterable<String> aStr = bs.getAllValidWords(b);
		// System.out.println(sw.elapsedTime());
		// for (String s : aStr)
		// System.out.println(s);

		In in = new In(args[0]);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		BoggleBoard board = new BoggleBoard(args[1]);
		int score = 0;
		Stopwatch sw = new Stopwatch();
		Iterable<String> words = solver.getAllValidWords(board);
		StdOut.println("Solved in " + sw.elapsedTime() + " ms.");
		for (String word : words) {
			StdOut.println(word);
			score += solver.scoreOf(word);
		}
		StdOut.println("Score = " + score);
	}
}
