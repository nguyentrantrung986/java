import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Solver {
	private int moves;
	private Stack<Board> solution;

	public Solver(Board initial) {
		if (initial == null)
			throw new java.lang.IllegalArgumentException();

		MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
		minPQ.insert(new SearchNode(initial, null, 0));
		minPQ.insert(new SearchNode(initial.twin(), null, 0));
		moves = -1;

		SearchNode currentNode;
		do {
			currentNode = minPQ.delMin();

			Iterable<Board> neighbors = currentNode.board.neighbors();
			for (Board b : neighbors) {
				// don't enqueue a neighbor if its Board is the same as the
				// Board of the predecessor search node.
				if (currentNode.prevNode == null || !b.equals(currentNode.prevNode.board))
					minPQ.insert(new SearchNode(b, currentNode, currentNode.moveCount + 1));
			}
		} while (!currentNode.board.isGoal());

		moves = currentNode.moveCount;
		solution = new Stack<>();

		do {
			solution.push(currentNode.board);
			currentNode = currentNode.prevNode;
		} while (currentNode != null);

		//if the first Board is the twin, the initial is not solvable
		Board firstBoard = solution.peek();
		if (!firstBoard.equals(initial)) {
			moves = -1;
			solution = null;
		}
	}

	public boolean isSolvable() {
		return !(solution == null);
	}

	public int moves() {
		return moves;
	}

	public Iterable<Board> solution() {
		return solution;
	}

	private class SearchNode implements Comparable<SearchNode> {
		SearchNode prevNode;
		private Board board;
		private final int moveCount;
		private final int priority;

		public SearchNode(Board currentB, SearchNode prevNode, int moveCount) {
			this.board = currentB;
			this.prevNode = prevNode;
			this.moveCount = moveCount;
			this.priority = moveCount + board.manhattan();
		}

		@Override
		public int compareTo(SearchNode that) {
			int priorityDiff = this.priority - that.priority;
			//break ties with manhattan
			 if(priorityDiff==0)
			 return board.manhattan() - that.board.manhattan();

			return priorityDiff;
		}
	}

	public static void main(String[] args) {

		// create initial Board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Stopwatch sw = new Stopwatch();
		Solver solver = new Solver(initial);
		StdOut.println("Solved in " + sw.elapsedTime() + " seconds");

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			for (Board Board : solver.solution()) {
				StdOut.println(Board);
				StdOut.println("Manhattan: " + Board.manhattan());
			}
			StdOut.println("Minimum number of moves = " + solver.moves());
		}
	}
}
