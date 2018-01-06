import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Solver {
	private int moves;
	private MinPQ<SearchNode> minPQ;
	private Stack<Board> solution;

	public Solver(Board initial) {
		if(initial == null) throw new java.lang.IllegalArgumentException();
		
		minPQ = new MinPQ<SearchNode>();
		minPQ.insert(new SearchNode(initial, null, 0));
		Stack<Board> exploredBoard = new Stack<>();
		moves = -1;

		SearchNode currentNode;
		do {
			currentNode = minPQ.delMin();
			exploredBoard.push(currentNode.board);

			Iterable<Board> neighbors = currentNode.board.neighbors();
			for (Board b : neighbors) {
				// don't enqueue a neighbor if its board is the same as the
				// board of the predecessor search node.
				if (currentNode.prevNode==null || !b.equals(currentNode.prevNode.board))
					minPQ.insert(new SearchNode(b, currentNode, currentNode.moveCount + 1));
			}
		} while (!currentNode.board.isGoal());
		
		if(currentNode.board.isGoal()){
			moves=currentNode.moveCount;
			solution = new Stack<>();
			
			do{
				solution.push(currentNode.board);
				currentNode = currentNode.prevNode;
			}while(currentNode.prevNode != null);
		}
	}
	
	public boolean isSolvable() {
		return !(solution==null);
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
			int priority = (moveCount + board.manhattan()) - (that.moveCount + that.board.manhattan());
//			
//			if(priority==0)
//				return board.hamming() - that.board.hamming();
			
			return priority;
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
	    Stopwatch sw = new Stopwatch();
	    Solver solver = new Solver(initial);
	    StdOut.println("Solved in "+sw.elapsedTime()+" seconds");
	    
	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {	        
	        for (Board board : solver.solution()){
	            StdOut.println(board);
	        	StdOut.println("Manhattan: " + board.manhattan());
	        }
	        StdOut.println("Minimum number of moves = " + solver.moves());
	    }
	}
}
