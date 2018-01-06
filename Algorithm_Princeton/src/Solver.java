import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private final Board initial;
	private int moves;
	private MinPQ<SearchNode> minPQ;
	private Queue<Board> solution;

	public Solver(Board initial) {
		this.initial = initial;
		minPQ = new MinPQ<SearchNode>();
		minPQ.insert(new SearchNode(initial, null, 0));
		solution = new Queue<>();
		moves = -1;

		SearchNode currentNode;
		do {
			currentNode = minPQ.delMin();
			solution.enqueue(currentNode.board);
			moves++;

			Iterable<Board> neighbors = currentNode.board.neighbors();
			for (Board b : neighbors) {
				// don't enqueue a neighbor if its board is the same as the
				// board of the predecessor search node.
				if (currentNode.prevNode==null || !b.equals(currentNode.prevNode.board))
					minPQ.insert(new SearchNode(b, currentNode, currentNode.moveCount + 1));
			}
		} while (!currentNode.board.isGoal());
	}
	
	public boolean isSolvable() {
		return true;
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

		public SearchNode(Board currentB, SearchNode prevNode, int moveCount) {
			this.board = currentB;
			this.prevNode = prevNode;
			this.moveCount = moveCount;
		}

		@Override
		public int compareTo(SearchNode that) {
			return (moveCount + board.manhattan()) - (that.moveCount + that.board.manhattan());
		}
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

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}
